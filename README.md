# Art-Museum-Search-Engine
The program makes queries using APIs of museums and art galleries based on specifications the user gives it.

# Authors, and credit 
Nazafarin Jalilian-NazafarinJ \
Yiwei Jiang-JYW5114 \
Chelsey Junting Wang-chlsey \
Kathleen Wang-kaz123456789 \
Frai Li-Frappey \
No Image by Adrien Coquet from https://thenounproject.com/browse/icons/term/no-image (CC BY 3.0) \
Jsoup library, for parsing HTML into plain text: https://jsoup.org/ 

### Project Specification for Group # 221
Team Name: Javaphiles 
Domain:
Art Museum search engine

### Project's purpose:
The program makes queries using APIs of museums and art galleries based on specifications the user gives it 
(e.g. The artist, whether a piece of art is on view in the museum) in the form of filters or searching for 
specific names, etc. and returns a collection of the art pieces as well as details such as the Artist credited, 
the museum which it currently belongs in, the date of acquisition of this artifact, 
and an image of the art piece(if available). The program also selectively adapts to each of the APIs queried 
since there are features of a museum/gallery API which might help with our query but is not present in every API. \ \
This project was made because it seemed like there were no single search engine that would be able to query museum 
databases specifically other than the search tied to respective museums, and what this program aims to do is to 
query multiple museums for results at the same time.

### Table of contents:
[Features](?tab=readme-ov-file#essential-features) \
[Installation](?tab=readme-ov-file#installation-instructions) \
[Usage](?tab=readme-ov-file#usage-guide) \
[License](?tab=readme-ov-file#license) 

### Essential Features
1. Search artworks by name, artist, etc. With a filter 
2. View artwork descriptions by clicking into the image of the artwork
3. Local rating and favoriting system, and the user is able to access the artworks that are favorited or rated. \
   (screenshots TBA)

### Installation instructions: 
The software works on Windows 10, MacOS Sonoma 14.6.1, and Ubuntu 22.04.5 LTS Linux, other operating systems are not tested. \
The software depends on version 1.18.1 of the Jsoup library for parsing HTML query results, 
version 4.12.0 of the okhttp library to read http, as well as version 20240303 of JSON. Junit 4.13.1,
Jacoco 0.8.12 and RELEASE version Jupiter are used for unit testing and coverage.


### Usage Guide
(video TBA) \
1. Searching artworks
Type your search query into the text box. Select any filters that you wish to be applied to your search.
Images of search results will pop up. Hover over them to see the name and artist of the artworks, click on them
to view detailed information. Use the back button to go back to your previous query results, or use clear to clear the 
queries and search again.
2. Viewing favorited/rated artworks
Click on the button in the main window, and all rated or favorited artworks will show up like search queries. 

### License
Program code is licensed under MIT License.

### (to be removed in final README) Proposed Entities for the Domain:
#### Artwork: 
- Title
- Time period
- Artist
- Gallery
- List of keywords
- Image

### (to be removed in final README) Proposed API for the project:
https://api.artic.edu/docs
API for the database of the Art institute of Chicago
https://metmuseum.github.io/ 
Provides an API for retrieving information about all the artworks in the Metropolitan Museum of Modern Art.

### (to be removed in final README) Scheduled Meeting Times + Mode of Communication:
Meeting time outside of lab: Tuesday Afternoon (flexible)
Mode of Communication: in person



### (to be removed in final README) User Stories:

1. Liam wants to take his friend to a magnificent art gallery and be their tour guide. By looking up the art pieces being offered with its associated information(i.e, title, year, etc) he can do it more efficiently. Either at the moment or once he is back from the gallery, he can rate the art work out of 5 stars.

2. James wants to view some artworks. He can smoothly scroll through a gallery of art pieces, each displayed with a clear title, artist name, date of acquisition, and brief description.

3. Bobby saw an artwork in the Metropolitan Museum of Art but needed to remember what the piece was called. Thankfully, he remembers the artist and searches it up. He is given the picture, title, location, time, artist name, etc… of the potential pieces it could be.

4. Emily uses our search engine to find artworks. She is able to favorite the ones she likes.

5. Anna wants to rate some artworks. She is able to rate them out of 5 stars. She is also able to change her mind and rate it a different score later.

6. Robby sees some artworks he REALLY hates. He is able to leave hate comments under the artwork page.
