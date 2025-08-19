1. Go to https://structurizr.com/dsl
2. Delete the current diagram
3. Copy the diagram code between the ``` ```
4. Paste the diagram code in the editor/dsl window
5. Click the the Render button

```structurizr
workspace {
    model {
        user = person "User"
        softwareSystem = softwareSystem "Software System (Encryption overview)" {
            webapp = container "Web Application" {
                user -> this "Uses"
            }
            wizard = container "BackendService" {
                webapp -> this "Sends/Receives Credentials"
            }
            vaultair = container "Vault" {
                wizard -> this "Encrypts/Decrypts credentials"
            }            
            database = container "Database" {
                vaultair -> this "Stores and retrieves encrypted credentials"
            }
        }
    }

    views {
        systemContext softwareSystem {
            include *
            autolayout lr
        }

        container softwareSystem {
            include *
            autolayout lr
        }

        theme default
    }

}
```