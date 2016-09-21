/*
 * 
 * Copyright (c) 2016 1&1 Internet SE.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 *        
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.oneandone.relesia.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;

import org.hibernate.MappingException;
import org.hibernate.boot.Metadata;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.util.MetadataHelper;
 
public class SQLScriptGenerator {
 
    public static void main(String[] args) throws MappingException, IOException {
    	
    	String createSQLFile = "dbscripts/createTables.sql";
    	String dropSQLFile = "dbscripts/dropTables.sql";
    	String hibernateCfgFile = "/db/hibernate.cfg.xml";
    	
    	final EnumSet<TargetType> targetTypes = EnumSet.noneOf( TargetType.class );
		targetTypes.add(TargetType.SCRIPT);
    	
		System.out.println("Initialize Hibernate configuration from " + hibernateCfgFile );
		
    	Configuration cfg = new Configuration().configure(hibernateCfgFile);
    	Metadata metadata = MetadataHelper.getMetadata(cfg);
    	
    	SchemaExport export = new SchemaExport();
		export.setHaltOnError(true);
		export.setFormat(true);
		export.setDelimiter(";");
		
		System.out.println("Generating create SQL to file " + createSQLFile );
		if ( new File(createSQLFile).exists() ) {
			Files.delete(Paths.get(createSQLFile));
		}
		export.setOutputFile(createSQLFile);
		export.execute(targetTypes, Action.CREATE, metadata);
		
		System.out.println("Generating drop SQL to file " + dropSQLFile );
		export.setOutputFile(dropSQLFile);
		if ( new File(dropSQLFile).exists() ) {
			Files.delete(Paths.get(dropSQLFile));
		}
		export.execute(targetTypes, Action.DROP, metadata);

		System.out.println("Done!");
	}
}