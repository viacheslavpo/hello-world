package com.cloudally.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class Zipper {

	public Zipper() {
		super();
	}
	
    public boolean unZip(String file, String outputDir){
    	try {
			java.util.zip.ZipFile zipFile = new ZipFile(file);
			try {
			  Enumeration<? extends ZipEntry> entries = zipFile.entries();
			  while (entries.hasMoreElements()) {
			    ZipEntry entry = entries.nextElement();
			    File entryDestination = new File(outputDir,  entry.getName());
			    if (entry.isDirectory()) {
			        entryDestination.mkdirs();
			    } else {
			        entryDestination.getParentFile().mkdirs();
			        InputStream in = zipFile.getInputStream(entry);
			        OutputStream out = new FileOutputStream(entryDestination);
			        IOUtils.copy(in, out);
			        IOUtils.closeQuietly(in);
			        out.close();
			    }
			  }
			} finally {
			  zipFile.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
   }

    public void zipFolder(final File folder, final File zipFile) {
    	OutputStream outputStream = null;
    	ZipOutputStream zipOutputStream = null;
        try {
        	outputStream = new FileOutputStream(zipFile);
        	zipOutputStream = new ZipOutputStream(outputStream);
        	processFolder(folder, zipOutputStream, folder.getPath().length() + 1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(zipOutputStream);
			IOUtils.closeQuietly(outputStream);
		}
    }

    private void processFolder(final File folder, final ZipOutputStream zipOutputStream, final int prefixLength)
            throws IOException {
        for (final File file : folder.listFiles()) {
            if (file.isFile()) {
                final ZipEntry zipEntry = new ZipEntry(file.getPath().substring(prefixLength));
                zipOutputStream.putNextEntry(zipEntry);

                FileInputStream inputStream = new FileInputStream(file);
                IOUtils.copy(inputStream, zipOutputStream);
                zipOutputStream.closeEntry();
                
                IOUtils.closeQuietly(inputStream);
//                IOUtils.closeQuietly(zipOutputStream);
            } else if (file.isDirectory()) {
                processFolder(file, zipOutputStream, prefixLength);
            }
        }
    }

	
}
