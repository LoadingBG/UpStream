# Contributing

## New methods
- Implement methods for all stream base classes for which it makes sense
- Implement methods from [the TODO list](./TODO.md).
  - If you think of a functionality currently not in the list, create an issue.

## Conventions
- New stream subclass:
  - Name: Base class stream prefix + Method name in base class with capital first letter + "Stream":
    - `Stream#enumerate` &#8594; `EnumerateStream`
    - `BiStream#map` &#8594; `BiMapStream`
- Creator methods:
  - Only `Stream` should contain creator methods.
- Javadoc formula:
    - One-sentence explanation
    - A more thorough explanation if needed
    - Examples if needed
    - Whether this is an intermediate operation or terminal operation:
        - `<p>This is an intermediate operation.</p>`
        - `<p>This is a terminal operation.</p>`
    - API or implementation notes if any
    - Parameter, return and throws docs in that order