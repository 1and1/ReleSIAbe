
    alter table siadb.APPLICATION 
        drop 
        foreign key FKbon46lq9d5u422jqn5yg6vlfu;

    alter table siadb.APPLICATION 
        drop 
        foreign key FK9j5pct564ngisy08ydu43xnl8;

    alter table siadb.APPLICATION 
        drop 
        foreign key FK6melu63psl2le8gev1prysbca;

    alter table siadb.APPLICATION 
        drop 
        foreign key FKq1l1x2ko3h4588n6ro04mcnjo;

    alter table siadb.APPLICATION 
        drop 
        foreign key FKfpbtdc8jmyo9tpwijyh2w8jr3;

    alter table siadb.CHECKER_PLUGIN 
        drop 
        foreign key FKnehfnq6aanxgkfk9k0prj9g5a;

    alter table siadb.CHECKER_PLUGIN 
        drop 
        foreign key FKojf5vne227wowhupyn9so7v3n;

    alter table siadb.ISSUE 
        drop 
        foreign key FKgoncmd9x3fkwttuol72pdfq9a;

    alter table siadb.PLUGIN_DEPENDENCY 
        drop 
        foreign key FKfvy5lhyswdym824rftpopd9kl;

    alter table siadb.PLUGIN_DEPENDENCY 
        drop 
        foreign key FKh6g33b4db58kv119575hbu5ql;

    alter table siadb.PLUGIN_PROPERTY 
        drop 
        foreign key FKcdatvd9fvwv1bol6scjctvc7c;

    alter table siadb.WORK_APPLICATION 
        drop 
        foreign key FKdcwfokaj1f29m9u6nrfvmuood;

    drop table if exists siadb.APPLICATION;

    drop table if exists siadb.BUILD_TYPE;

    drop table if exists siadb.CHECKER_PLUGIN;

    drop table if exists siadb.CHECKERLIST;

    drop table if exists siadb.ERROR;

    drop table if exists siadb.ISSUE;

    drop table if exists siadb.PLUGIN;

    drop table if exists siadb.PLUGIN_DEPENDENCY;

    drop table if exists siadb.PLUGIN_PROPERTY;

    drop table if exists siadb.PRODUCT;

    drop table if exists siadb.PROGRAMMING_LANGUAGE;

    drop table if exists siadb.RELEASE_REPOSITORY;

    drop table if exists siadb.SCM;

    drop table if exists siadb.USER;

    drop table if exists siadb.WORK_APPLICATION;

    drop table if exists hibernate_sequence;
