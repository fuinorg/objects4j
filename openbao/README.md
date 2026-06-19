# objects4j-openbao

[OpenBao](https://github.com/openbao/openbao) based implementation of the [objects4j-crypto](../crypto) encryption contract.

## Description
[BaoEncryptedDataService](src/main/java/org/fuin/objects4j/openbao/BaoEncryptedDataService.java) implements
[EncryptedDataService](../crypto/src/main/java/org/fuin/objects4j/crypto/EncryptedDataService.java) using OpenBao's
**Transit secrets engine** ("encryption as a service"). Key material never leaves the server, keys are versioned and can be
rotated, and the returned ciphertext (`vault:vN:base64`) embeds the key version used for encryption.

It talks to the server over HTTP using the JDK `java.net.http.HttpClient` and jakarta JSON-P &ndash; no third-party Vault
client is required. As OpenBao is API-compatible with HashiCorp Vault, the same service also works against a Vault server.

## Contract mapping

| `EncryptedDataService` method | Transit endpoint |
|:------------------------------|:-----------------|
| `createTransitEngine()` (setup) | `POST sys/mounts/transit` (idempotent)              |
| `keyExists(id)`                 | `GET transit/keys/<id>`                             |
| `createKey(id)`                 | `POST transit/keys/<id>`                            |
| `rotateKey(id)`                 | `POST transit/keys/<id>/rotate`                     |
| `getKeyVersion(id)`             | `GET transit/keys/<id>` &rarr; `latest_version`     |
| `encrypt(id, ...)`              | `POST transit/encrypt/<id>`                         |
| `decrypt(data)`                 | `POST transit/decrypt/<id>`                         |

`EncryptionKeyVersionUnknownException` is part of the contract but is never thrown by this implementation because Transit
resolves the key version directly from the ciphertext.

## Usage

```java
// Base URL + an authentication token (here the dev-mode root token)
final BaoEncryptedDataService service = new BaoEncryptedDataService("http://localhost:8200", "root");

// Mount the Transit engine once (idempotent), then create a key
service.createTransitEngine();
service.createKey("user-data");

// Encrypt / decrypt
final byte[] clear = "Some secret".getBytes(StandardCharsets.UTF_8);
final EncryptedData encrypted = service.encrypt("user-data", "Greeting", "text/plain", clear);
final byte[] decrypted = service.decrypt(encrypted); // uses the key version embedded in the ciphertext
```

A custom Transit mount path can be supplied via the
`BaoEncryptedDataService(baseUrl, token, mountPath)` constructor (default mount path is `transit`).

## Testing
[BaoEncryptedDataServiceTest](src/test/java/org/fuin/objects4j/openbao/BaoEncryptedDataServiceTest.java) is a
[Testcontainers](https://github.com/testcontainers/testcontainers-java) integration test that boots the real
`openbao/openbao` image (dev mode) and exercises the full API (create, encrypt/decrypt round-trip, key rotation, and the
error paths). It requires a running Docker environment and is **skipped automatically** when none is available
(`@Testcontainers(disabledWithoutDocker = true)`).
