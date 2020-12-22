```bash
#
# registers a new user
#Hibernate se não passar ou passar zero no ID tem se a exceção de que a tabela está detached
curl -H "Content-Type: application/json" -X POST -d '{
    "id": -1,
    "username": "admin",
    "password": "password", 
    "authorities": [
            {
                "id": -1,
                "name": "ROLE_ADMIN"
            },
            {
                "id": -1,
                "name": "ROLE_USER"
            }
        ]    
}' http://localhost:8080/user/sign-up

# log into the application (JWT is generated), que será retornado no header da requisição.
curl -i -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/login

# issue a POST request, passing the JWT, to create a task
# remember to replace xxx.yyy.zzz with the JWT retrieved above
curl -H "Content-Type: application/json" \
-H "Authorization: Bearer xxx.yyy.zzz" \
-X POST -d '{
    "description": "Buy watermelon"
}'  http://localhost:8080/user/all

```


token expirado para validar.

Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNQVJDRUxPIiwiYXV0aG9yaXRpZXMiOiJST0xFX0FETUlOLFJPTEVfVVNFUiwiLCJleHAiOjE1OTE1NzQ5OTF9.zBxDXcDZoGsxxh9LVYwRJ7ifXxjVE05VfF8LvPH3EY8GQGQPyZuKQs3b1lzfT6OmEOD2kfusHpvQ0qLNbAKE-Q

Token Valido 
eyJhbGciOiJIUzUxMiJ9.eyJhdXRvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX0dFUkVOVEUifSx7ImF1dGhvcml0eSI6IlJPTEVfTU9ERVJBRE9SIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sInN1YiI6Im1hcmNlbG8iLCJleHAiOjE1OTIwOTgxNDN9.1LvDC2WkwDS0UI1o6vyH3o72K1JytHCZtHfZ67dj-0evdv1z2TFhstp4Ehx1IypJlyDo0ieX_UpZHFk-3OxUlA

eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXJjZWxvIiwiYXV0aG9yaXRpZXMiOiJST0xFX0FETUlOLFJPTEVfVVNFUiwiLCJleHAiOjE1OTM1MzM5NjN9.QF77u9EEOamkPJU6FZcKvSjBexvauDCDST9pGePwBIkbE-21zONmop3NprrQzvVu78hGKnBkwHDoVDQr6w6Urg