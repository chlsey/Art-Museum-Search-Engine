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
The program makes queries using APIs of museums and art galleries based on specifications the user gives it (e.g. Historical period where the artifact belongs, Form of art, Nationality, Style) in the form of Tags or searching for specific names, etc. and returns a collection of all the art pieces as well as details such as the Artist credited, the museum which it currently belongs in, the date of acquisition of this artifact, and an image of the art piece(if available). The program would also selectively “adapt” to each of the APIs of different museums since it’s a possibility that there are features of a museum/gallery API which might help with our java.interface_adapters.search query but is not present in every API.

### User Stories: 
1. James wants to view some artworks. He can smoothly scroll through a gallery of art pieces, each displayed with a clear title, artist name, composition date, and brief description.

2. Bobby saw an artwork in the Metropolitan Museum of Art but needed to remember what the piece was called. Thankfully, he remembers the general period and searches it up. He is given the picture, title, location, time, artist name, etc… of the potential pieces it could be.

3. Emily uses our search engine to find artworks. She is able to favorite the ones she likes.

4. Anna wants to rate some artworks. She is able to rate them out of 5 stars.

5. Robby sees some artworks he REALLY hates. He is able to leave hate comments under the artwork page.

6. Liam wants to take the rest of 1D to a magnificent art gallery and be their tour guide. By looking up every art piece with all its associated information, including people's comments about it, he can do it smoothly. 

### Proposed Entities for the Domain:
#### Artwork: 
- Title
- Time period
- Artist
- Gallery
- List of keywords
- Image

#### Artist:
- Name
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

### Credits 
No Image image by Adrien Coquet from https://thenounproject.com/browse/icons/term/no-image/ No Image IconsNoun Project (CC BY 3.0)
