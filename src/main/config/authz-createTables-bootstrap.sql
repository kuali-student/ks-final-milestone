
    create table AUTHZ (
        id varchar(255) not null,
        descendTree smallint not null,
        OBJ_ID varchar(36),
        VER_NBR integer,
        qualifier_id varchar(255),
        role_id varchar(255),
        principal_id varchar(255)
    );

    create table Permission (
        id varchar(255) not null,
        name varchar(255),
        OBJ_ID varchar(36),
        VER_NBR integer
    );

    create table Principal (
        id varchar(255) not null,
        name varchar(255),
        OBJ_ID varchar(36),
        VER_NBR integer
    );

    create table Qualifier (
        id varchar(255) not null,
        name varchar(255),
        OBJ_ID varchar(36),
        VER_NBR integer,
        leftVisit integer not null,
        rightVisit integer not null,
        parent_id varchar(255),
        qualifierType_id varchar(255),
        compositeQualifier_id varchar(255),
        qualifierHierarchy_id varchar(255)
    );

    create table QualifierHierarchy (
        id varchar(255) not null,
        name varchar(255),
        OBJ_ID varchar(36),
        VER_NBR integer
    );

    create table QualifierHierarchy_QualifierType (
        qualifierHierarchys_id varchar(255) not null,
        qualifierTypes_id varchar(255) not null
    );

    create table QualifierType (
        id varchar(255) not null,
        name varchar(255),
        composite smallint not null,
        OBJ_ID varchar(36),
        VER_NBR integer,
        compositeQualifierType_id varchar(255)
    );

    create table Role (
        id varchar(255) not null,
        name varchar(255),
        OBJ_ID varchar(36),
        VER_NBR integer,
        qualifierType_id varchar(255),
        qualifierHierarchy_id varchar(255)
    );

    create table Role_Permission (
        roles_id varchar(255) not null,
        permissions_id varchar(255) not null
    );