package com.gcp.recruitRight.Impls;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gcp.recruitRight.Repository.LoginServiceRepository;
import com.gcp.recruitRight.Repository.ProcessProfileRepository;
import com.gcp.recruitRight.models.Requirement;
import com.gcp.recruitRight.models.User;
import com.gcp.recruitRight.models.UserProfile;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

@Component
public class ProcessProfileImpl {
	
	@Autowired
	ProcessProfileRepository processProfileRepository;
	
	@Autowired
	LoginServiceRepository loginServiceRepository;
	
	Logger log = LoggerFactory.getLogger(ProcessProfileImpl.class);
	
	@Scheduled(cron = "0 15 01 * * ?") 				// Scheduled at 10:00AM every day(second minute hour day-of-month month day-of-week)
	public void processProfiles() throws IOException {
		
		List<Requirement> requirements = processProfileRepository.fetchActiveRequirements();
		log.info("Entering scheduled process");
		
		for(Requirement req:requirements)
		{
			int reqId = req.getReqId();
			String jobRole = req.getJobRole();
			String domain = req.getIsu();
			String techStack[] = req.getTechStack().split(",",0);
			double experience = req.getExperience();
			
			List<UserProfile> userProfiles = processProfileRepository.fetchActiveUserProfiles();
			
			for(UserProfile userProfile:userProfiles)
			{
				// get UserId
				String userId = userProfile.getUserId();
				
				// Create text string builder
			    StringBuilder text = new StringBuilder();
			    
			    InputStream inp = userProfile.getResume();
			    
			    //Create PdfReader instance.
			    PdfReader pdfReader = new PdfReader(inp);
			    
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
				//System.out.println(resumeData);
				
				String rdl = resumeData.toLowerCase();
				
				double score=0;
				
				if(!rdl.contains("experience"))
				{
					//Extract Technical Skills with out Exp
					String Fre_Skills = resumeData.substring(resumeData.indexOf("TECHNICAL KNOWLEDGE")+"TECHNICAL KNOWLEDGE".length()+2, resumeData.indexOf("ACADEMIC PROJECTS"));
					score = Freshers(techStack,experience,Fre_Skills);
				}
				else
				{
					score = Experienced(jobRole,domain,techStack,experience,resumeData);
				}
				
				double profileScore = score / (techStack.length)*100;
				
				processProfileRepository.updateProfileScore(reqId, userId, profileScore);
				
				inp.close();
				pdfReader.close();
			}
			processProfileRepository.updateProfileScoreStatus(reqId,2);
		}
		
		processProfileRepository.updateUserProfileStatus();
		sendEmail();
		log.info("Exiting processProfiles()");
	}
	
	public double Freshers(String techStack[], double experience, String Fre_Skills)
	  {
		log.info("Entering Freshers()");
		double score=0;
		String FS_lower = Fre_Skills.toLowerCase();
		if(experience==0)
		{
			
			for(int j=0;j<techStack.length;j++)
			{
				if(FS_lower.contains(techStack[j].toLowerCase()))
				{
					score+=1;
				}
			}
		}
		log.info("Exiting Freshers()");
		return score;
	  }
	  
	  public double Experienced(String jobRole, String domain, String techStack[], double experience, String resumeData)
	  {
		  log.info("Entering Experienced()");
		String Exp = resumeData.substring(resumeData.indexOf("EXPERIENCE")+2+"EXPERIENCE".length(), resumeData.indexOf("ACADEMIC")-4);
		  
		double score=0;
		float expy = 0;
		String Skills = "";
		String lines[] = Exp.split("\n");
		
		for(int i=1;i<lines.length;i++)
		{
			String A[] = lines[i].split(" ");
			if(jobRole.equalsIgnoreCase(A[3]))
			{
				if(domain.equalsIgnoreCase(A[1]))
				{
					expy+=Float.parseFloat(A[5]);
					Skills+=A[4];
				}
			}
		}
		
		if(experience==0)
		{
			String Fre_Skills = resumeData.substring(resumeData.indexOf("TECHNICAL KNOWLEDGE")+"TECHNICAL KNOWLEDGE".length()+2, resumeData.indexOf("ACADEMIC PROJECTS"));
			Skills = Skills+" "+Fre_Skills;
		}
		
		Skills = Skills.toLowerCase();
		
		if(experience<=expy)
		{
			for(int j=0;j<techStack.length;j++)
			{
				if(Skills.contains(techStack[j].toLowerCase()))
				{
					score+=1;
				}
			}
		}
		log.info("Exiting Experienced()");
		return score;
	  }
	  
	  
	  	public void send(int reqId, String to, String sub, String msg, final String user,final String pass, List<UserProfile> profiles)
		{   
		  log.info("Entering send() for mail");
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");		
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance
					(
						props,new javax.mail.Authenticator()
						{
							protected PasswordAuthentication getPasswordAuthentication() 
							{
							return new PasswordAuthentication(user,pass);
							}
						}
					); 
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(user));
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
				message.setSubject(sub);
				
				Multipart multipart = new MimeMultipart();
				
				MimeBodyPart messagePart = new MimeBodyPart();
				multipart.addBodyPart(messagePart);
				messagePart.setText(msg);
				
				StringBuilder tableData = new StringBuilder();
				tableData.append(
				"<html>" +
						"<style> table, th, td {border:1px solid black; border-style: double; </style>" + 
						"<body>" + 
							"<table border=1 cellpadding=5>" + 
								"<tr> <th>Name</th> <th>Email Id</th> <th>Contact</th> <th>Profile Score</th> </tr>"
						);
							
				for(UserProfile usp: profiles)
				  {
					  InputStream resume = usp.getResume();
					  String name = usp.getName();
					  
					  double score=processProfileRepository.fetchProfileScores(reqId,usp.getUserId());
					  
					  tableData.append("<tr> <td> "+usp.getName()+"</td> <td>"+usp.getUserId()+"</td> <td>"+usp.getContact()+"</td> <td>"+Double.toString(score)+"</td> </tr>\r\n");
					  
					  addAttachment(multipart, name, resume);
					  
					  processProfileRepository.insertIntoFeedback(reqId,usp.getUserId(),usp.getName(),usp.getContact(),score,"InProgress");
				  }
				
				tableData.append("</table> </br></br></br><p>Thanks & Regards,</p><p>TCS Recruit Right Team</p></body> </html>");
				
				MimeBodyPart htmlBodyPart = new MimeBodyPart(); //4
				htmlBodyPart.setContent(tableData.toString() , "text/html"); //5
				multipart.addBodyPart(htmlBodyPart); 
				message.setContent(multipart);
				Transport.send(message);
				log.info("Exiting send() for mail");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	  
	  public void addAttachment(Multipart multipart, String name, InputStream resume)
	  {
		  try {
			  ByteArrayDataSource dataSource = new ByteArrayDataSource(resume, "application/pdf");
			  MimeBodyPart attachmentPart = new MimeBodyPart();
			  attachmentPart.setDataHandler(new DataHandler(dataSource));
			  attachmentPart.setFileName(name+".pdf");
			  multipart.addBodyPart(attachmentPart);
			  
		  }catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }
	  
	  public void sendEmail()
	  {
		  try {
			  log.info("Entering sendEmail()");
			  List<Requirement> requirements = processProfileRepository.fetchActiveRequirements();
			  	
			  String User="recruitright.team@gmail.com";
	          String Pass="recruitright";
	          //String Message = "Hello,\nFind attached profiles with maximum similarity for requirement posted in Recruit Right.\n\n";     
			  
			  for(Requirement req:requirements)
			  {
				  int reqId = req.getReqId();
				  String toEmail = req.getUserId();
				  
				  User user = loginServiceRepository.fetchUserById(toEmail);
				  
				  String Message = "Hello "+user.getFirstName()+",\nFind attached profiles with maximum similarity for requirement posted in Recruit Right.\n\n"; 
				  
				  String Subject="Filtered Profiles: "+req.getProjectName()+"(ReqId: "+Integer.toString(reqId)+')';
				  List<UserProfile> profiles = processProfileRepository.fetchTopProfiles(reqId);
				  if(profiles.size()>0)
				  {
					  log.info("Sending Mail...");
					  send(reqId, toEmail, Subject, Message, User, Pass, profiles);
			          log.info("Mail Sent Successfully...");
			          processProfileRepository.updateRequirementStatus(reqId);
				  }
			  }
			  log.info("Exiting sendMail()");
	            
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	  }

}
