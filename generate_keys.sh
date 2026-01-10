mkdir jwt

openssl genrsa -out jwt/private-rsa.pem 2048

openssl pkcs8 -topk8 -nocrypt \
  -inform PEM \
  -in jwt/private-rsa.pem \
  -outform PEM \
  -out jwt/private.pem

openssl rsa -pubout \
  -in jwt/private-rsa.pem \
  -out jwt/public.pem

chmod 600 jwt/private-rsa.pem
chmod 600 jwt/private.pem
chmod 644 jwt/public.pem
