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

package com.oneandone.relesia.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileServices {
	
	public void delete(File file){
		
		if(!file.exists()){
			return;
		}
		if(file.isDirectory()){
			if(file.list().length==0){
				file.delete();
			}else{
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if(file.list().length==0){
					file.delete();
				}
			}
		}else{
			file.delete();
		}
	}
	
	public String findDir(File root, String name)
	{
		
		if (root.getName().equals(name))
		{
			return root.getAbsolutePath();
		}
		
		File[] files = root.listFiles();
		
		if(files != null)
		{
			for (File f : files)  
			{
				if(f.isDirectory())
				{   
					String myResult = findDir(f, name);
					if (myResult == null) {
						continue;
					}
					else {
						return myResult;
					}
				}
			}
		}
		
		return null;
	}
	
	public File findFile(String path, String fName) {
		File f = new File(path);
		if (fName.equals(f.getName())) return f;
		if (f.isDirectory()) {
			for (String aChild : f.list()) {
				File ff = findFile(path + File.separator + aChild, fName);
				if (ff != null) return ff;
			}
		}
		return null;
	}
	
	public void copyFolder(String source, String destination){
		
		Path sourceParentFolder = Paths.get(source);
		Path destinationParentFolder = Paths.get(destination);
		Stream<Path> allFilesPathStream;
		try {
			allFilesPathStream = Files.walk(sourceParentFolder);
			Consumer<? super Path> action = new Consumer<Path>(){
				
				@Override
				public void accept(Path t) {
					try {
						String destinationPath = t.toString().replaceAll(sourceParentFolder.toString(), destinationParentFolder.toString());
						Files.copy(t, Paths.get(destinationPath));
					} 
					catch(FileAlreadyExistsException e){
						e.getMessage();
					}
					catch (IOException e) {
						e.getMessage();
					}
					
				}
				
			};
			allFilesPathStream.forEach(action );
			
		} catch(FileAlreadyExistsException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		
	}
	
}
