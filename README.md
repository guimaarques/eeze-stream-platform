# Eeze Stream Platform Test 

Here is a #howtouse and #howtosetup this project

### SetUp
For a setup, you just need an IDE and jdk-17. In this project I've tried to use simple tools to make it easier to run.

### HowToUse

Once It's all set, you may want to try it out. In the package tree, we have a folder called "collections". 
There you'll find collection to import on your postman. Also, we have in src/resource a simple index.html that will load the video that you published via API and start it on you favorite browser.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Available APIs

#### POST - /eeze-platform/videos
Description: upload a video in database.
Parameters: 
- Name: video name - String
- File: the archive itself - MultipartFile
Response: expected to see your just uploaded video

        {
            "name": "edicaointeressante.mp4",
            "metadata": {}
        }

#### PUT - /eeze-platform/videos/{name}
Description: add metadatas to your video.
Parameters: 
- Name: video name - String
- Body: metadatas for your video - JSON (example bellow)

        {
            "title":"How to lose a guy in 10 days",
            "synopisis":"romance",
            "director":"Isabella",
            "cast":"warner",
            "release_date":"yesterday",
            "genre":"romcom",
            "running_time":"one hour"
        }
  
#### GET - /eeze-platform/videos/{name}/play
Description: execute your video and increase video's views
Parameters:
- Name: video name - String
Response: bytes of a video

### GET - /eeze-platform/videos/{name}
Description: retrieve all videos' names available
Parameters:
- Name: video name - String
Response: video details for a name

        {
            "name": "edicaointeressante.mp4",
            "metadata": {
                "title": "How to lose a guy in 10 days",
                "synopisis": "romance",
                "director": "Isabella",
                "cast": "warner",
                "release_date": "yesterday",
                "genre": "romcom",
                "running_time": "one hour"
            }
        }

### GET - /eeze-platform/videos
Description: retrieve all videos names available with all its informations.
Parameters: none
Response: list of videos names

    [
        {
            "name": "test1.mp4"
        },
        {
            "name": "test2.mp4"
        }
    ]

### GET - /eeze-platform/videos/{name}/statistics
Description: retrieve statistics for a video
Parameters:
- Name: video name - String
Response: statistics for a video
Example:

          {
              "views": 23,
              "impressions": 3
          }

### GET - /eeze-platform/videos/query
Description: retrieve videos from a dynamic metadata attribute. You can search for a video using any attribute.
Paramenter:
- field: metadata name. Object. Example: director
- value: matadata value. Object. Example: Marques
Response: list of videos
Example:

        [
            {
                "name": "edicaointeressante.mp4",
                "metadata": {
                    "title": "How to lose a guy in 10 days",
                    "synopisis": "romance",
                    "director": "Isabella",
                    "cast": "warner",
                    "release_date": "yesterday",
                    "genre": "romcom",
                    "running_time": "one hour"
                }
            }
        ]

### DELETE - /eeze-platform/videos/{name}
Description: delist your video from the catalog
Paramenter:
- Name: video name. String
Response: no response just status code.

-------

### Opportunities & Backlogs

##### Opportunities
- Migrate database to a NoSQL BD such as DynamoDB.
- Run this application in resilient scalable environment.
- Create more statistics metrics.
- Create observability to check application resilience.

##### Backlogs
- On method increaseVideoStatistic - improve solution of metric implementation
- On Dynamic Query API - implement the "field" evaluation so we can refuse an invalid field
- Add Swagger API for API auto documentation 
