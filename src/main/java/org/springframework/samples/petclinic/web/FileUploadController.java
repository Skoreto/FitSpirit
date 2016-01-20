package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.ServerRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class FileUploadController {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	// BUG Nutné mìnit apsolutní cestu ke složce "static" v projektu.
	private String myProjectPath = "C:\\Users\\Tomas\\Documents\\workspace-sts-3.7.2.RELEASE\\petclinic\\src\\main\\webapp\\static";
	
    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
//                String rootPath = System.getProperty("catalina.home");
                
                String rootPath = myProjectPath;
                       
                File dir = new File(rootPath + File.separator + "slozkaObrazku");
                
                if (!dir.exists())
                    dir.mkdirs();
                 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + name + ".jpg");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Umisteni souboru na Serveru = " + serverFile.getAbsolutePath());
 
                return "Uspesne se podarilo nahrat soubor = " + name;
            } catch (Exception e) {
                return "Nepodarilo se uploadnout " + name + " => " + e.getMessage();
            }
        } else {
            return "Nepodarilo se uploadnout " + name + " protoze soubor je prazdny.";
        }
    }
 
}
