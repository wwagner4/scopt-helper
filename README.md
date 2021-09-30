# Scopt Helper
Various functions to speedup development of command line interfaces based on 
the scala library [scopt](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#links).

## Examples
### Subcommands where each command fills its own case class.

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

