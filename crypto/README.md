# objects4j-crypto
A small, algorithm-agnostic contract for encrypting and decrypting data using versioned secret keys.

## Description
This module does **not** contain any cryptographic implementation. It only defines the *contract* (interfaces, a
data container and the related exceptions) that an application implements with the cipher, key store and key rotation
strategy of its choice. Keeping the contract separate allows different projects to share the same abstraction and the
same serializable encrypted-data representation.

All types are immutable value objects / interfaces and carry no dependency beyond [objects4j-common](../common).

## Interfaces

### [EncryptedData](src/main/java/org/fuin/objects4j/crypto/EncryptedData.java)
Container for encrypted data. Besides the encrypted bytes themselves it carries the information required to decrypt
them again later: the identifier and version of the key used, plus the original data type and content type.

| Method               | Description                                                                  |
|----------------------|------------------------------------------------------------------------------|
| `getKeyId()`         | Unique identifier of the secret key used.                                    |
| `getKeyVersion()`    | Version of the secret key used (stamped at encryption time for rotation).    |
| `getDataType()`      | Unique type of the original data, e.g. `"UserPersonalData"`.                 |
| `getContentType()`   | Content type of the original data, e.g. `"application/json; encoding=UTF-8"`.|
| `getEncryptedData()` | The encrypted bytes.                                                         |

### [EncryptedDataService](src/main/java/org/fuin/objects4j/crypto/EncryptedDataService.java)
Service that performs the actual encryption / decryption and manages versioned secret keys.

```Java
// Make sure a key exists
if (!service.keyExists("user-data")) {
    service.createKey("user-data");
}

// Encrypt
final byte[] clear = "Some secret".getBytes(StandardCharsets.UTF_8);
final EncryptedData encrypted = service.encrypt("user-data", "Greeting", "text/plain", clear);

// Decrypt (uses the key version stored inside the EncryptedData,
// so historical data still decrypts after a rotateKey(...))
final byte[] decrypted = service.decrypt(encrypted);
```

Only the `keyId` and the `data` are actively used for encryption; `dataType` and `contentType` are stored in the
resulting [EncryptedData](src/main/java/org/fuin/objects4j/crypto/EncryptedData.java) for information purposes so the
caller can transform the decrypted bytes back into the original object.

## Exceptions
All exceptions implement [ExceptionShortIdentifable](../common/src/main/java/org/fuin/objects4j/common/ExceptionShortIdentifable.java)
and expose a stable `SHORT_ID` (prefixed with `OBJECTS4J-CRYPTO`, see
[Objects4JCryptoUtils](src/main/java/org/fuin/objects4j/crypto/Objects4JCryptoUtils.java)).

| Exception                                                                                                              | Short ID                                       | Thrown when                                                  |
|----------------------------------------------------------------------------------------------------------------------|------------------------------------------------|-------------------------------------------------------------|
| [DecryptionFailedException](src/main/java/org/fuin/objects4j/crypto/DecryptionFailedException.java)                   | `OBJECTS4J-CRYPTO-DECRYPTION_FAILED`           | Decrypting the data with the key / version failed.          |
| [DuplicateEncryptionKeyIdException](src/main/java/org/fuin/objects4j/crypto/DuplicateEncryptionKeyIdException.java)   | `OBJECTS4J-CRYPTO-DUPLICATE-ENCRYPTION_KEY_ID` | Creating a key with an identifier that already exists.       |
| [EncryptionKeyIdUnknownException](src/main/java/org/fuin/objects4j/crypto/EncryptionKeyIdUnknownException.java)       | `OBJECTS4J-CRYPTO-ENCRYPTION_KEY_ID_UNKNOWN`   | A key with the given identifier is unknown.                 |
| [EncryptionKeyVersionUnknownException](src/main/java/org/fuin/objects4j/crypto/EncryptionKeyVersionUnknownException.java) | `OBJECTS4J-CRYPTO-ENCRYPTION_KEY_VERSION_UNKNOWN` | The requested version of the key is unknown.            |

## Implementing the contract
Provide your own `EncryptedDataService` (backed by a JCE cipher, a cloud KMS, Vault, ...) and a serializable
`EncryptedData` for the binding you use. For example, [event-store-commons](https://github.com/fuinorg/event-store-commons)
builds on these types to transparently encrypt event data, shipping JSON-B / JAXB / Jackson representations of
`EncryptedData` and an encrypting event store decorator.
