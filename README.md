# Kotlinmon-Api
This is a simple API that allows you to get information about Pokémon. 
It was created using the Spring Boot framework and the data was obtained from the [PokéAPI](https://pokeapi.co/).

## Endpoints
- **GET** `/pokemon/{name}`: Get information about a Pokémon by its name.
- **GET** `/pokemon/{id}`: Get information about a Pokémon by its id.
- **GET** `/pokemon/ability/{name}`: Get information about a Ability by its name.
- **GET** `/pokemon/ability/{id}`: Get information about a Ability by its id.

Example:

localhost:8080/api/pokemon/ditto
```json

{
  "name":"ditto",
  "height":3,
  "weight":40,
  "abilities":[
    {
      "is_hidden":false,
      "slot":1,
      "ability":
      {
        "name":"limber"
      }
    },
    {
      "is_hidden":true,
      "slot":3,
      "ability":
      {
        "name":"imposter"
      }
    }
  ]
}
```
localhost:8080/api/ability/stench
```json
{
  "name":"stench"
}
```