# Scopt Helper
Various functions to speedup development of command line interfaces based on 
the scala library [scopt](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#links).

## Examples

### List with id and description

Mount Wilson classes

A luminosity classification known as the Mount Wilson system was used to distinguish between stars of different luminosities.
This notation system is still sometimes seen on modern spectra.


ID | Description  | Full Description
---|------------|----
sd | Subdwarf   | Sometimes denoted by "sd", is a star with luminosity class VI under the Yerkes spectral classification system. They are defined as stars with luminosity 1.5 to 2 magnitudes lower than that of main-sequence stars of the same spectral type. On a Hertzsprung–Russell diagram subdwarfs appear to lie below the main sequence.
d  | Dwarf      | A dwarf is a star of relatively small size and low luminosity. Most main sequence stars are dwarf stars. The term was originally coined in 1906 when the Danish astronomer Ejnar Hertzsprung noticed that the reddest stars—classified as K and M in the Harvard scheme—could be divided into two distinct groups. They are either much brighter than the Sun, or much fainter
sg | Subgiant   | A subgiant is a star that is brighter than a normal main-sequence star of the same spectral class, but not as bright as giant stars. The term subgiant is applied both to a particular spectral luminosity class and to a stage in the evolution of a star.
g  | Giant      | A giant star is a star with substantially larger radius and luminosity than a main-sequence (or dwarf) star of the same surface temperature.[1] They lie above the main sequence (luminosity class V in the Yerkes spectral classification) on the Hertzsprung–Russell diagram and correspond to luminosity classes II and III. The terms giant and dwarf were coined for stars of quite different luminosity despite similar temperature or spectral type by Ejnar Hertzsprung about 1905
sg | Supergiant | Supergiants are among the most massive and most luminous stars. Supergiant stars occupy the top region of the Hertzsprung–Russell diagram with absolute visual magnitudes between about −3 and −8. The temperature range of supergiant stars spans from about 3,400 K to over 20,000 K.


### Subcommands where each command fills its own case class.

Let's cook something

Concept
```
Cooking
    - vegan boolean
    shopping
        - number: int 
        - description: str
    prepare
        - number of persons: int
        - motto: str
```

