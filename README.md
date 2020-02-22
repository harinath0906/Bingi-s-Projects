# Respository of project I work on to ease some manual tasks

## Code to download all videos from a Youtube Playlist URL

### download_videos_from_youtubeplaylist.groovy

This code will take playlistURL, OutputFolder as inputs. This code will skim through the page source to identify URLs of each video of the playlist, the code will them also get its video URL and will download the videos to OutputFolder. The code first looks for 720p, then 480p, then 360p while downloading.

Sample playlist URL - https://www.youtube.com/playlist?list=PLQY2H8rRoyvwLbzbnKJ59NkZvQAW9wLbx

Libraries used - Apache Commons IO, Apache Commons Lang3
