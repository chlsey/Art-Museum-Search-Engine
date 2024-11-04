Nazafarin Jalilian-NazafarinJ \
Yiwei Jiang-JYW5114 \
Chelsey Junting Wang-chlsey \
Kathleen Wang-kaz123456789 \
Frai Li-Frappey 


# Art-Museum-Search-Engine
The program makes queries using APIs of museums and art galleries based on specifications the user gives it.


### Project Specification for Group # 221
Team Name: Javaphiles 
Domain:
Art Museum search engine

### Software Specification:
The program makes queries using APIs of museums and art galleries based on specifications the user gives it (e.g. Historical period where the artifact belongs, Form of art, Nationality, Style) in the form of Tags or searching for specific names, etc. and returns a collection of all the art pieces as well as details such as the Artist credited, the museum which it currently belongs in, the date of acquisition of this artifact, and an image of the art piece(if available). The program would also selectively “adapt” to each of the APIs of different museums since it’s a possibility that there are features of a museum/gallery API which might help with our search query but is not present in every API.

### User Stories: 
1. James wants to view some artworks. He can smoothly scroll through a gallery of art pieces, each displayed with a clear title, artist name, composition date, and brief description.

2. Bobby saw an artwork in the Metropolitan Museum of Art but needed to remember what the piece was called. Thankfully, he remembers the genre and general period and searches it up. He is given the picture, title, location, time, artist name, etc… of the potential pieces it could be.

3. Emily, an art history professor, prepares a public service lecture on Renaissance art aimed at a non-specialist audience. She wants to recommend art exhibits that her audience can visit. So she turns to the program to search for artworks from the Renaissance period, filtering by nearby museums and galleries. The program adapts to each museum's API and retrieves relevant pieces, providing details like the artist, museum location, and available images. With this information, Emily can recommend specific Renaissance artworks that her audience can easily visit in local museums.

4. Anna is a high school student who is looking for some artworks for her Art project. She is not sure about a specific painting that she wants, but she does know there are some keywords she is looking for. She enters some keywords related to her project (e.g., "impressionism," "blue tones," "nature," or "19th-century art") in the Art Museum search engine and it will provide her with a list of artworks that match the entered keywords. 

5. Robby is visiting Toronto and wants to see impressionist paintings, so he searches for the genre and city. He is given the titles, images, and other information of the paintings that match this description.

6. Liam wants to take the rest of 1D to a magnificent art gallery and be their tour guide so he can prove that singing is not his only talent. By looking up every art piece with all its associated information, he can do it smoothly. 

### Proposed Entities for the Domain:
#### Artwork: 
- Time period
- Artist
- Gallery
- Genre
- List of keywords
- Image

#### Artist:
- Name
- Image
- Time period
- List of artworks
- Gender

#### Museum/Gallery:
- Location
- List of artworks

### Proposed API for the project:
https://collection.cooperhewitt.org/api
Provides many different APIs for retrieving information about galleries, artworks, and artists in their art collections.
https://metmuseum.github.io/ 
Provides an API for retrieving information about all the artworks in the Metropolitan Museum of Modern Art.

### Scheduled Meeting Times + Mode of Communication:
Meeting time outside of lab: Tuesday Afternoon (flexible)
Mode of Communication: in person

