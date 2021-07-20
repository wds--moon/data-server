/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.28.785 on 2021-03-29 17:21:12.

export interface DmDataSourceDto extends Serializable {
    id?: number;
    name?: string;
    dbType?: DbTypes;
    host?: string;
    port?: number;
    username?: string;
    database?: string;
    version?: number;
    remark?: string;
    properties?: { [index: string]: string };
    password?: string;
}

export interface ColumnMetaData extends Serializable {
    columnName?: string;
    columnLabel?: string;
    columnType?: number;
    columnTypeName?: string;
}

export interface SqlMetaData extends Serializable {
    parameters?: string[];
    columns?: ColumnMetaData[];
}

export interface SvcDto extends Serializable {
    id?: number;
    connectionId?: number;
    sql?: string;
    name?: string;
    description?: string;
    requiredParameters?: Parameter[];
    parameters?: Parameter[];
    orders?: Order[];
    labels?: string[];
    columns?: { [index: string]: string };
    version?: number;
}

export interface SvcInfo extends Serializable {
    id?: number;
    name?: string;
    connection?: DataSourceProperties;
    sql?: string;
    parameters?: Parameter[];
    orders?: Order[];
}

export interface TableMetaDto extends Serializable {
    name?: string;
    type?: string;
}

export interface Serializable {
}

export interface Parameter extends Serializable {
    column?: string;
    description?: string;
    operator?: string;
    parameterName?: string;
}

export interface Order extends Serializable {
    column?: string;
    direction?: Direction;
}

export interface DataSourceProperties extends Serializable {
    dbType?: DbTypes;
    host?: string;
    port?: number;
    username?: string;
    password?: string;
    database?: string;
    additionalProperties?: { [index: string]: string };
    key?: string;
}

export const enum DbTypes {
    MySQL8 = "MySQL8",
    SQLSERVER = "SQLSERVER",
}

export const enum Direction {
    ASC = "ASC",
    DESC = "DESC",
}
