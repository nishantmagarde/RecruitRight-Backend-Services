package com.gcp.recruitRight.Impls;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gcp.recruitRight.Repository.UploadProfileRepository;
import com.gcp.recruitRight.Util.UsernameStorage;
import com.gcp.recruitRight.models.UserProfile;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

@Service
public class UploadProfileImpl {
	
	@Autowired
	UploadProfileRepository uploadProfileRepository;

	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	Logger log = LoggerFactory.getLogger(UploadProfileImpl.class);
	
	public List<String> uploadProfile(MultipartFile[] resumeList) throws Exception{
			
			log.info("Entering UploadProfileImpl.uploadProfiles()");
			List<String> incorrectProfiles = new ArrayList<String>();
			
			try {
				String uploader = UsernameStorage.getUserId();
				String userId;
				String contact;
				String name;
				int status = 0;
					
				//List<File> userProfileList = uploadProfileRequest.getUserProfileList();
				for(int i=0;i<resumeList.length;i++)
				{
				    StringBuilder text = new StringBuilder();
				    
					try {
						
						//Create PdfReader instance.
						PdfReader pdfReader = new PdfReader(resumeList[i].getBytes());	
					 
						//Get the number of pages in pdf.
						int pages = pdfReader.getNumberOfPages(); 
					
						//Iterate the pdf through pages.
						for(int page=1; page<=pages; page++) 
						{ 
						  //Extract the page content using PdfTextExtractor.
						  String pageContent = PdfTextExtractor.getTextFromPage(pdfReader, page);
						  text.append(pageContent);
					    }
						String resumeData = text.toString();
						
						String Emp_details = resumeData.substring(0, resumeData.indexOf("CAREER OBJECTIVE")-2);
						
						String Emp_details_list[] = Emp_details.split("\n");
						
						name = Emp_details_list[0];
						contact = Emp_details_list[1].split(" ")[1];
						log.info("Contact number lenghth");
						System.out.println(contact.length());
						if(contact.length()!=10)
						{
							throw new Exception("Invalid Mobile Number");
						}
						userId = Emp_details_list[2].split(" ")[1];
						
						pdfReader.close();
					}
					catch(Exception e)
					{
						log.info(resumeList[i].getOriginalFilename());
						incorrectProfiles.add(resumeList[i].getOriginalFilename());
						log.info(e.getMessage());
						continue;
					}
					
					List<UserProfile> profiles = uploadProfileRepository.findUserProfiles(userId);
					
					if(profiles.size()!=0) {
						status += uploadProfileRepository.updateUserProfiles(userId,resumeList[i],uploader);
					}
					else
						status += uploadProfileRepository.insertIntoUserProfiles(userId,name,contact,resumeList[i],uploader);
				}
				if(status == resumeList.length) {
					log.info("Exiting UploadProfileImpl.uploadProfile()--->true");
				}
				log.info("Exiting UploadProfileImpl.uploadProfile()--->false");
				return incorrectProfiles;
		} 
			catch(Exception e) {
				e.printStackTrace();
			throw new Exception("Error while uploading profile");
		}
	}
}