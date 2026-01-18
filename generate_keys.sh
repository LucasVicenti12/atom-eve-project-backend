mkdir src\main\resources\jwt

openssl genpkey -algorithm RSA -out src/main/resources/jwt/private.pem -pkeyopt rsa_keygen_bits:2048

openssl rsa -pubout -in src/main/resources/jwt/private.pem -out src/main/resources/jwt/public.pem
