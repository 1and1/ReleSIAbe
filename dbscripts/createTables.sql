
    create table siadb.APPLICATION (
        ID bigint not null auto_increment,
        NAME varchar(150) not null,
        RELEASE_LOCATION varchar(250),
        SNAPSHOT_LOCATION varchar(250),
        SOURCE_PATH varchar(250),
        BUILD_TYPE_ID bigint not null,
        LANGUAGE_ID bigint not null,
        PRODUCT_ID bigint not null,
        RELEASE_REPO_ID bigint not null,
        SCM_ID bigint not null,
        primary key (ID)
    );

    create table siadb.BUILD_TYPE (
        ID bigint not null auto_increment,
        HAS_AUTH bit not null,
        HAS_BUILD bit not null,
        HAS_DEPENDENCY bit not null,
        HAS_RELEASE bit not null,
        HAS_STAGING bit not null,
        TOOL_NAME varchar(150) not null,
        TOOL_VERSION varchar(150) not null,
        primary key (ID)
    );

    create table siadb.CHECKER_PLUGIN (
        ID bigint not null,
        POSITION integer,
        CHECKERLIST_ID bigint,
        PLUGIN_ID bigint,
        primary key (ID)
    );

    create table siadb.CHECKERLIST (
        ID bigint not null auto_increment,
        NAME varchar(250) not null,
        primary key (ID)
    );

    create table siadb.ERROR (
        ID bigint not null auto_increment,
        DESCRIPTION varchar(250) not null,
        ERRORCODE integer not null,
        ERRORTYPE varchar(50) not null,
        SEVERITY varchar(50) not null,
        primary key (ID)
    );

    create table siadb.ISSUE (
        ID bigint not null auto_increment,
        ASSIGNEE varchar(150),
        DESCRIPTION varchar(250),
        ISSUE_TYPE varchar(150) not null,
        REFFERENCE_PATH varchar(250) not null,
        STATUS varchar(50) not null,
        SUBJECT varchar(250) not null,
        APPLICATION_ID bigint not null,
        primary key (ID)
    );

    create table siadb.PLUGIN (
        ID bigint not null auto_increment,
        CLASS_NAME varchar(150) not null,
        NAME varchar(150) not null,
        TYPE varchar(50) not null,
        primary key (ID)
    );

    create table siadb.PLUGIN_DEPENDENCY (
        PLUGIN_ID bigint not null,
        DEPENDENCY_ID bigint not null
    );

    create table siadb.PLUGIN_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(150) not null,
        VALUE varchar(150) not null,
        PLUGIN_ID bigint,
        primary key (ID)
    );

    create table siadb.PRODUCT (
        ID bigint not null auto_increment,
        NAME varchar(150) not null,
        primary key (ID)
    );

    create table siadb.PROGRAMMING_LANGUAGE (
        ID bigint not null auto_increment,
        NAME varchar(150) not null,
        VERSION varchar(150) not null,
        primary key (ID)
    );

    create table siadb.RELEASE_REPOSITORY (
        ID bigint not null auto_increment,
        NAME varchar(150) not null,
        HAS_AUTH bit not null,
        TYPE varchar(150) not null,
        URL varchar(150) not null,
        primary key (ID)
    );

    create table siadb.SCM (
        ID bigint not null auto_increment,
        NAME varchar(150) not null,
        HAS_AUTH bit not null,
        TYPE varchar(150) not null,
        URL varchar(150) not null,
        primary key (ID)
    );

    create table siadb.USER (
        ID bigint not null auto_increment,
        EMAIL varchar(250) not null,
        FIRST_NAME varchar(250) not null,
        LAST_MODIFIED datetime not null,
        LAST_NAME varchar(250) not null,
        PASSWORD varchar(250) not null,
        primary key (ID)
    );

    create table siadb.WORK_APPLICATION (
        ID bigint not null auto_increment,
        LAST_MODIFIED datetime not null,
        LOCAL_PATH varchar(250),
        NAME varchar(150) not null,
        TAG varchar(250),
        APPLICATION_ID bigint not null,
        primary key (ID)
    );
create index IDX5f2v9kro2xygok4cdd0q56ok3 on siadb.APPLICATION (ID, PRODUCT_ID, NAME);

    alter table siadb.APPLICATION 
        add constraint UK_rejo33hwc77rtxolc4q80ahje unique (NAME);
create index IDX9nrx34p97r71vvxxinns7n29y on siadb.BUILD_TYPE (ID, TOOL_NAME);
create index IDX7pyoxxbbeflg98jyog0u52796 on siadb.CHECKER_PLUGIN (ID);
create index IDXp60ohd9g41omkjgnpp6ttasnj on siadb.CHECKERLIST (ID);

    alter table siadb.ERROR 
        add constraint UK_mluikkpb9af7obmicceuv7x75 unique (ERRORCODE);
create index IDXjjd6snquvpv4t7u18umb31amh on siadb.ISSUE (ID, APPLICATION_ID, STATUS, ISSUE_TYPE);
create index IDXrpui74uadroee41cdxkl946dg on siadb.PLUGIN (ID);

    alter table siadb.PLUGIN 
        add constraint UK_55u1fhfowabb7u2febkssqce0 unique (NAME);
create index IDXpwmpqrnrpoy2xqdwkstvm7os6 on siadb.PLUGIN_PROPERTY (ID, PLUGIN_ID);
create index IDXbk1t0mxmyy8n44lcg96788t1u on siadb.PRODUCT (ID, NAME);

    alter table siadb.PRODUCT 
        add constraint UK_lh7nqls96dm61kob7cd8d00s3 unique (NAME);
create index IDXka7h4y40g5gkihsj11ruojj1v on siadb.PROGRAMMING_LANGUAGE (ID);

    alter table siadb.PROGRAMMING_LANGUAGE 
        add constraint UK_sr75v047bt8owidpobx4x8rgd unique (NAME);

    alter table siadb.PROGRAMMING_LANGUAGE 
        add constraint UK_jv5ie0bhje67f9ok4qmt8whi0 unique (VERSION);
create index IDXh3r0h7egs0575uigobfr8xt8n on siadb.RELEASE_REPOSITORY (ID, TYPE);
create index IDX157qare0nt8846glhsnq7eqj5 on siadb.SCM (ID, TYPE);
create index IDXd24nmserf6ojequa5lrt2scs6 on siadb.USER (ID, EMAIL);
create index IDXj3xe2yfl285i8k87ew76djvxi on siadb.WORK_APPLICATION (ID, APPLICATION_ID);

    create table hibernate_sequence (
        next_val bigint
    );

    insert into hibernate_sequence values ( 1 );

    alter table siadb.APPLICATION 
        add constraint FKbon46lq9d5u422jqn5yg6vlfu 
        foreign key (BUILD_TYPE_ID) 
        references siadb.BUILD_TYPE (ID);

    alter table siadb.APPLICATION 
        add constraint FK9j5pct564ngisy08ydu43xnl8 
        foreign key (LANGUAGE_ID) 
        references siadb.PROGRAMMING_LANGUAGE (ID);

    alter table siadb.APPLICATION 
        add constraint FK6melu63psl2le8gev1prysbca 
        foreign key (PRODUCT_ID) 
        references siadb.PRODUCT (ID);

    alter table siadb.APPLICATION 
        add constraint FKq1l1x2ko3h4588n6ro04mcnjo 
        foreign key (RELEASE_REPO_ID) 
        references siadb.RELEASE_REPOSITORY (ID);

    alter table siadb.APPLICATION 
        add constraint FKfpbtdc8jmyo9tpwijyh2w8jr3 
        foreign key (SCM_ID) 
        references siadb.SCM (ID);

    alter table siadb.CHECKER_PLUGIN 
        add constraint FKnehfnq6aanxgkfk9k0prj9g5a 
        foreign key (CHECKERLIST_ID) 
        references siadb.CHECKERLIST (ID);

    alter table siadb.CHECKER_PLUGIN 
        add constraint FKojf5vne227wowhupyn9so7v3n 
        foreign key (PLUGIN_ID) 
        references siadb.PLUGIN (ID);

    alter table siadb.ISSUE 
        add constraint FKgoncmd9x3fkwttuol72pdfq9a 
        foreign key (APPLICATION_ID) 
        references siadb.APPLICATION (ID);

    alter table siadb.PLUGIN_DEPENDENCY 
        add constraint FKfvy5lhyswdym824rftpopd9kl 
        foreign key (DEPENDENCY_ID) 
        references siadb.PLUGIN (ID);

    alter table siadb.PLUGIN_DEPENDENCY 
        add constraint FKh6g33b4db58kv119575hbu5ql 
        foreign key (PLUGIN_ID) 
        references siadb.PLUGIN (ID);

    alter table siadb.PLUGIN_PROPERTY 
        add constraint FKcdatvd9fvwv1bol6scjctvc7c 
        foreign key (PLUGIN_ID) 
        references siadb.PLUGIN (ID);

    alter table siadb.WORK_APPLICATION 
        add constraint FKdcwfokaj1f29m9u6nrfvmuood 
        foreign key (APPLICATION_ID) 
        references siadb.APPLICATION (ID);
