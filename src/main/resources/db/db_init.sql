-- Plugins definitions
INSERT INTO `PLUGIN` (ID, NAME, TYPE, CLASS_NAME) VALUES (1, 'Readme Checker', 'CHECKER', 'com.oneandone.relesia.checker.ReadmeChecker');
INSERT INTO `PLUGIN` (ID, NAME, TYPE, CLASS_NAME) VALUES (2, 'Readme Content Checker', 'CHECKER', 'com.oneandone.relesia.checker.ReadmeContentChecker');
INSERT INTO `PLUGIN` (ID, NAME, TYPE, CLASS_NAME) VALUES (3, 'Pom Checker', 'CHECKER', 'com.oneandone.relesia.checker.PomChecker');
INSERT INTO `PLUGIN` (ID, NAME, TYPE, CLASS_NAME) VALUES (4, 'Changelog Checker', 'CHECKER', 'com.oneandone.relesia.checker.ChangelogChecker');

INSERT INTO `PLUGIN_PROPERTY` (ID, NAME, VALUE, PLUGIN_ID) VALUES (1,'SAMPLEpropertyA','ValueA',1),(2,'SAMPLEpropertyB','ValueB',1);

-- Plugin Dependencies. Meaning: first depends on the second
INSERT INTO `PLUGIN_DEPENDENCY` (PLUGIN_ID, DEPENDENCY_ID) VALUES (2, 1);

-- Errors    
INSERT INTO `ERROR` (ID, DESCRIPTION, ERRORCODE, ERRORTYPE, SEVERITY) VALUES (1,'the room is on fire pls help',112,'RELEASE','CRITICAL');
INSERT INTO `ERROR` (ID, DESCRIPTION, ERRORCODE, ERRORTYPE, SEVERITY) VALUES (2,'so let me be understood this is a big mistake',113,'BUILD','BLOCKING');
INSERT INTO `ERROR` (ID, DESCRIPTION, ERRORCODE, ERRORTYPE, SEVERITY) VALUES (3,'jmp, jmp, cmp, mov eax, ebx ',114,'DOCUMENTATION','RISK');
INSERT INTO `ERROR` (ID, DESCRIPTION, ERRORCODE, ERRORTYPE, SEVERITY) VALUES (4,'what is RISC? how about ARM?',9,'CONFIGURATION','BLOCKING');
INSERT INTO `ERROR` (ID, DESCRIPTION, ERRORCODE, ERRORTYPE, SEVERITY) VALUES (5,'The content was not found, amazing.',404,'COMPILE','BLOCKING');
INSERT INTO `ERROR` (ID, DESCRIPTION, ERRORCODE, ERRORTYPE, SEVERITY) VALUES (6,'The server is on fire, please jump on the nearest window',500,'RELEASE','CRITICAL');
INSERT INTO `ERROR` (ID, DESCRIPTION, ERRORCODE, ERRORTYPE, SEVERITY) VALUES (7,'911 emergencies; https://www.youtube.com/watch?v=YoTIaRyGzac',911,'RELEASE','CRITICAL');
