# Trivial URL Shortner

| 👉 **Education purpose only**. Project doesn't follow best practices nor intended to do so. It solely exists for author's experiments with Clojure and its environment.

<br>

Trivial URL shorther implementation. It takes a URL as an input and returns its 8 character long slug. Shorther exposes API endpoints to create and retrieve URLs. Data is stored in relational database.

<br>

## API

### Create a short URL

```
POST /api/redirect
Content-Type: application/json
{
  "url": string
}
```


### Follow a redirect

```
GET /:slug
```

E.g. `0.0.0.0:port/ABCDEFGH`.
