# -*- coding: utf-8 -*-
# !/usr/bin/env python
"""SQL migration for api-gateway."""
import yaml
import os

from pydantic import BaseSettings

FILTER_MAP = {
    "RequestBodyCheck":
        ["RequestBodyParse", "AppSignCheck", "AccessTokenCheck",
         "RequestBodyDecrypt", "ContentTypeChange", "RequestBodyGetData"],
    "HeaderCheck": ["HeaderCheck"],
    "ResponseBodyCheck": ["ResponseBodyAesEncrypt", "SignResponse"]}


class Settings(BaseSettings):  # pylint: disable=too-few-public-methods
    """Base Setting."""

    yaml_file_name: str = None
    sql_file_name: str = "migration.sql"

    class Config:  # pylint: disable=too-few-public-methods
        """Base config for setting."""

        env_file = "./migration_config.env"
        env_file_encoding = "utf-8"
        case_sensitive = False


def read_yaml(file_name):
    """Read the content map from yaml file."""
    cur_path = os.path.dirname(os.path.realpath(__file__))
    yaml_path = os.path.join(cur_path, file_name)

    with open(yaml_path, 'r', encoding="utf-8") as file:
        contents = file.read()
        content_dict = yaml.load(contents, Loader=yaml.FullLoader)
        return content_dict


def get_routes(content_dict):
    """Get the routes from yaml contents."""
    try:
        # return content_dict["spring"]["cloud"]["gateway"]["routes"]  # for dev yaml
        return content_dict["spring"]["redis"]["gateway"]["routes"]  # for prod yaml
    except ValueError as e:
        print(f"No such value: {e}")


def generate_sql(routes_list):
    """Generate the sql commands from route list."""
    total_sql = list()
    for route in routes_list:
        gateway_id = route.get("id", None)
        uri = route.get("uri", None)
        predicate = route.get("predicate")
        predicate_path = None
        if predicate:
            predicate_path = predicate[0].split("=")[1]
        filters_list = route.get("filters")
        filters = list()
        parts_key = None
        for each_filter in filters_list:
            if isinstance(each_filter, str):
                if each_filter.lower().find("rewritepath") != -1:
                    rewrite_path = each_filter.split("=")[1]
                    regular_path, actual_path = rewrite_path.split(",")
                elif each_filter.lower().find("stripprefix") != -1:
                    parts_key = each_filter.split("=")[1]
                else:
                    print("Special string need to check: ", each_filter)
            elif isinstance(each_filter, dict):
                origin_filter = each_filter.get("name")
                filters.append(origin_filter)
            else:
                raise TypeError(f"Can not parse the {type(each_filter)}.")

        nacos_sql = "Insert into cfg_nacos(gateway_id, uri, predicate, " \
                    f"regular_path, actual_path, parts_key) values " \
                    f"('{gateway_id}', '{uri}', '{predicate_path}', " \
                    f"'{regular_path}', '{actual_path}', '{parts_key}');"
        nacos_sql = nacos_sql.replace("'None'", "null")
        total_sql.append(nacos_sql)

        for each_filter in filters:
            mapping_filters = FILTER_MAP.get(each_filter, None)
            if mapping_filters:
                filters_sql = [f"Insert into cfg_filter(gateway_id, value) "
                               f"VALUES ('{gateway_id}', '{each}');"
                               for each in mapping_filters]
                total_sql.extend(filters_sql)

    return total_sql


def generate_sql_file(file_name, sql_list):
    """Generate the sql file with content_list."""
    directory = os.path.dirname(os.path.realpath(Settings.Config.env_file))
    file_path = os.path.join(directory, file_name)
    with open(file_path, "w", encoding="utf-8") as file:
        for sql in sql_list:
            file.write(sql + "\n")


if __name__ == "__main__":
    settings = Settings()
    yaml_content = read_yaml(settings.yaml_file_name)
    print(f"yaml_content: {yaml_content}")
    routes = get_routes(yaml_content)
    print(f"routes: {routes}")
    total_sql = generate_sql(routes)
    print(f"total_sql: {total_sql}")
    generate_sql_file(settings.sql_file_name, total_sql)
    print("Sql file has been generated.")
