	update AUTHZ set VER_NBR = 0;
	update Permission set VER_NBR = 0;
	update Principal set VER_NBR = 0;
	update Qualifier set VER_NBR = 0;
	update QLF_HIERARCHY set VER_NBR = 0;
	update QualifierType set VER_NBR = 0;
	update Role set VER_NBR = 0;
	
	alter table AUTHZ
		add primary key (id);
	
	alter table Permission
		add primary key (id);
	
	alter table Principal
		add primary key (id);
	
	alter table Qualifier
		add primary key (id);
	
	alter table QLF_HIERARCHY
		add primary key (id);
	
	alter table QualifierType
		add primary key (id);
	
	alter table Role
		add primary key (id);
						
    alter table AUTHZ 
        add constraint FK3BBDFB227C42442 
        foreign key (principal_id) 
        references Principal;

    alter table AUTHZ 
        add constraint FK3BBDFB2E1CC9FB2 
        foreign key (role_id) 
        references Role;

    alter table AUTHZ 
        add constraint FK3BBDFB29CC82C2 
        foreign key (qualifier_id) 
        references Qualifier;

    alter table Qualifier 
        add constraint FK2D10108AE99A30A2 
        foreign key (qualifierType_id) 
        references QualifierType;

    alter table Qualifier 
        add constraint FK2D10108A84DB62C9 
        foreign key (compositeQualifier_id) 
        references Qualifier;

    alter table Qualifier 
        add constraint FK2D10108A8E886AC2 
        foreign key (parent_id) 
        references Qualifier;

    alter table Qualifier 
        add constraint FK2D10108A760E0172 
        foreign key (qualifierHierarchy_id) 
        references QLF_HIERARCHY;

    alter table QLF_HIERARCHY_QualifierType 
        add constraint FKDFB06E7051A2415 
        foreign key (qualifierHierarchys_id) 
        references QLF_HIERARCHY;

    alter table QLF_HIERARCHY_QualifierType 
        add constraint FKDFB06E7014F53597 
        foreign key (qualifierTypes_id) 
        references QualifierType;

    alter table QualifierType 
        add constraint FKE48BEE4829BB529 
        foreign key (compositeQualifierType_id) 
        references QualifierType;

    alter table Role 
        add constraint FK26F496E99A30A2 
        foreign key (qualifierType_id) 
        references QualifierType;

    alter table Role 
        add constraint FK26F496760E0172 
        foreign key (qualifierHierarchy_id) 
        references QLF_HIERARCHY;

    alter table Role_Permission 
        add constraint FKF8A569387FE5180B 
        foreign key (roles_id) 
        references Role;

    alter table Role_Permission 
        add constraint FKF8A56938DE71243D 
        foreign key (permissions_id) 
        references Permission;
