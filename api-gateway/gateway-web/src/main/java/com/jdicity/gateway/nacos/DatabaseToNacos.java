package com.jdicity.gateway.nacos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jdicity.gateway.entity.ApiForDatabase;
import com.jdicity.gateway.entity.CfgForDatabase;
import com.jdicity.gateway.mapper.ApiForDatabaseMapper;
import com.jdicity.gateway.mapper.ApiInfoMapper;
import com.jdicity.gateway.mapper.CfgForDatabaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/20 15:32
 */

@Service
public class DatabaseToNacos {
    @Autowired
    private ApiForDatabaseMapper apiForDatabaseMapper;

    @Autowired
    private ApiInfoMapper apiInfoMapper;

    @Autowired
    private CfgForDatabaseMapper cfgForDatabaseMapper;

    public String getGatewayContent(long apiId) {
        ApiForDatabase content = apiForDatabaseMapper.findById(apiId);
        ApiForNacos apiForNacos = new ApiForNacos();

        //设置Path断言为前端路径，路径最后必须是api名称，如：/a/b/api_name
        MyPredicate pathPredicate = new MyPredicate();
        pathPredicate.setName("Path");
        Map<String, String> pathMap = new HashMap<>();
        pathMap.put("pattern", content.getFrontPath());
        pathPredicate.setArgs(pathMap);

        //将两个断言放入gateway中
        List<MyPredicate> predicateList = new ArrayList<>();
        predicateList.add(pathPredicate);
        apiForNacos.setPredicates(predicateList);

        //将SetPath过滤器设置为后端路径
        MyFilter setPathFilter = new MyFilter();
        setPathFilter.setName("SetPath");
        Map<String, String> setPathMap = new HashMap<>();
        setPathMap.put("template", content.getBackPath());
        setPathFilter.setArgs(setPathMap);

        //将api中所有的过滤器名称拿出来放入过滤器中
        List<MyFilter> filterList = new ArrayList<>();
        filterList.add(setPathFilter);
        for (String filterName : content.getFilterName()) {
            MyFilter filter = new MyFilter();
            filter.setName(filterName);
            filterList.add(filter);
        }

        //将所有过滤器放入gateway中
        apiForNacos.setFilters(filterList);

        //设置gateway的id
        apiForNacos.setId(content.getId() + "");

        //设置gateway要转发到的uri
        apiForNacos.setUri(content.getBackAddr());

        return new Gson().toJson(apiForNacos);
    }

    //将小程序路由相关的内容推到nacos上
    public List<ApiForNacos> getCfgContent() {
        //拿到小程序的全部nacos配置
        List<CfgForDatabase> cfgInfoList = cfgForDatabaseMapper.findCfgInfo();
        List<ApiForNacos> apiForNacosList = new ArrayList<>();

        for (CfgForDatabase cfgInfo : cfgInfoList) {
            //设置uri和id
            ApiForNacos apiForNacos = new ApiForNacos();
            apiForNacos.setId(cfgInfo.getGatewayId());
            apiForNacos.setUri(cfgInfo.getUri());

            //设置Path断言
            MyPredicate pathPredicate = new MyPredicate();
            pathPredicate.setName("Path");
            Map<String, String> pathMap = new HashMap<>();
            pathMap.put("pattern", cfgInfo.getPredicate());
            pathPredicate.setArgs(pathMap);
            List<MyPredicate> myPredicates = new ArrayList<>();
            myPredicates.add(pathPredicate);
            apiForNacos.setPredicates(myPredicates);

            List<MyFilter> myFilters = new ArrayList<>();

            //设置RewritePath过滤器
            if (cfgInfo.getRegularPath() != null && cfgInfo.getActualPath() != null) {
                MyFilter rewritePathFilter = new MyFilter();
                rewritePathFilter.setName("RewritePath");
                Map<String, String> rewritePathMap = new HashMap<>();
                rewritePathMap.put("regexp", cfgInfo.getRegularPath());
                rewritePathMap.put("replacement", cfgInfo.getActualPath());
                rewritePathFilter.setArgs(rewritePathMap);
                myFilters.add(rewritePathFilter);
            } else {
                //设置StripPrefix过滤器
                MyFilter stripPrefixFilter = new MyFilter();
                stripPrefixFilter.setName("StripPrefix");
                Map<String, String> stripPrefixMap = new HashMap<>();
                stripPrefixMap.put("parts", cfgInfo.getPartsKey());
                stripPrefixFilter.setArgs(stripPrefixMap);
                myFilters.add(stripPrefixFilter);
            }

            for (String filter : cfgInfo.getFilters()) {
                MyFilter myFilter = new MyFilter();
                myFilter.setName(filter);
                myFilters.add(myFilter);
            }
            apiForNacos.setFilters(myFilters);
            apiForNacosList.add(apiForNacos);
        }

        //返回所有的小程序nacos配置的集合
        return apiForNacosList;
    }

    public String getGatewayContents() {
        List<String> apiGatewayList = new ArrayList<>();
        List<Long> allIds = apiInfoMapper.findAllIds();
        for (Long id : allIds) {
            String gatewayContent = getGatewayContent(id);
            apiGatewayList.add(gatewayContent);
        }

        //拼接小程序的nacos网关数据
        List<ApiForNacos> cfgContentList = getCfgContent();
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        for (ApiForNacos cfgContent : cfgContentList) {
            apiGatewayList.add(gb.create().toJson(cfgContent));
        }
        return apiGatewayList.toString();
    }
}
