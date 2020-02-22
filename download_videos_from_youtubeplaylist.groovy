import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import groovy.json.JsonSlurper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.io.FileUtils;

String playlistURL = "https://www.youtube.com/playlist?list=PLQY2H8rRoyvwLbzbnKJ59NkZvQAW9wLbx"
   
String linksfilename = (new Date().format("dd-MM-yyyy-HH.mm.ss"))+".txt"
URL playobj = new URL(playlistURL);
 ArrayList<String> hrefs=new ArrayList<String>();
HttpURLConnection conm = (HttpURLConnection) playobj.openConnection();
        conm.setRequestMethod("GET");
       // con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCodeconm = conm.getResponseCode();
        System.out.println("GET Response Code :: " + responseCodeconm);
        if (responseCodeconm == HttpURLConnection.HTTP_OK) { // success
            BufferedReader inrm = new BufferedReader(new InputStreamReader(conm.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = inrm.readLine()) != null) {
                response.append(inputLine);
            }
            inrm.close();

            // print result
         String outputsource = response.toString();
       //   new File("D:\\Scripts\\scrap2.txt").append(outputsource)
         String[] allhrefs = outputsource.split('<a class="pl-video-title-link yt-uix-tile-link yt-uix-sessionlink  spf-link " dir="ltr" href="');
        // println(allhrefs)
         
        
         
         for (int i=1;i <allhrefs.size();i++ )
         {
         hrefs.add(allhrefs[i].split("&amp")[0].trim())
         }
         println(hrefs)
//        / println(jsonst) window["ytInitialData"] =
        // def jsonSlurper = new JsonSlurper()
         //def jsonobject = jsonSlurper.parseText(jsonst)
         //new File("D:\\Scripts\\scrap2.txt").append(jsonobject.contents)
       
        
        } else {
            System.out.println("GET request not worked");
        }
        
        for (int i1=0;i1<hrefs.size();i1++)
        {
            
            String vidurl = "https://www.youtube.com"+hrefs.get(i1);
            
            println("Trying to download video from "+vidurl)
    String OutputFolder = "D:\\Scripts\\downloadfiles\\"

URL obj = new URL(vidurl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
       // con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader inr = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = inr.readLine()) != null) {
                response.append(inputLine);
            }
            inr.close();

            // print result
         String outputsource = response.toString();
         String jsonst = outputsource.split("ytplayer.config =")[1].split("ytplayer.load =")[0].trim();
//        / println(jsonst)
         def jsonSlurper = new JsonSlurper()
         def jsonobject = jsonSlurper.parseText(jsonst)
        
         def playerresponsestring =  jsonobject.args.player_response;
         def playerresponseobject =  jsonSlurper.parseText(playerresponsestring)
        //new File("D:\\Scripts\\scrap.txt").append(playerresponseobject.streamingData.formats[1].mimeType.split(" ")[0].replace(";",""))
       //qualityLabel
       int k
       Map urls = [:]
       for (int i=0;i< playerresponseobject.streamingData.formats.size();i++)
       {
      /* if(playerresponseobject.streamingData.formats[i].qualityLabel.equalsIgnoreCase("720p"))
       {
       k=i;
       }*/
       urls.put(playerresponseobject.streamingData.formats[i].qualityLabel,i)
       //   println(playerresponseobject.streamingData.formats[i].url)
         // println("----")
       }
       if (urls.containsKey("720p"))
       {
       k = urls.get("720p")
       }
       else if (urls.containsKey("480p"))
       {
       k = urls.get("480p")
       }
       else if (urls.containsKey("360p"))
       {
       k = urls.get("360p")
       }
       String filename = playerresponseobject.videoDetails.title.replaceAll("[^a-zA-Z0-9\\s+]", "");
       String extension = playerresponseobject.streamingData.formats[k].mimeType.split(" ")[0].replace(";","")
       
       if (extension.contains("/"))
       {
       extension = extension.split("/")[1].trim();
       }
      
   //  println(extension)
       URL downwebsite = new URL(playerresponseobject.streamingData.formats[k].url);
	    new File(OutputFolder+linksfilename).append(playerresponseobject.streamingData.formats[k].url+"\n")
String fullfilename = OutputFolder+i1+". "+filename+"."+extension
 File downfile = new File(fullfilename) ;
// println(OutputFolder+filename)
//below line downloads the file from URL
//FileUtils.copyURLToFile(downwebsite, downfile)
println("Video "+downfile+" downloaded to " +fullfilename)
        } else {
            System.out.println("GET request not worked");
        }
        }