# Config Vault

## Database

### Setup

1. Activate database secret engines

```bash
vault secrets enable -path=mongodb database
```

2. Configure vault with database connection

```bash
vault write database/config/mongodb \
  plugin_name=mongodb-database-plugin \
  allowed_roles="myuser" \
  connection_url="mongodb://{{username}}:{{password}}@mongodb.inventory-team3-dev.svc.cluster.local:27017/admin?tls=true" \
  username="root" \
  password="rootpassword"
```

3. Create a role that maps a name in Vault to an SQL statement to execute to create the database credential

```bash
vault write database/roles/myuser \
  db_name=my-mongodb-database \
  creation_statements='{ "db": "admin", "roles": [{ "role": "readWrite" }, {"role": "readWrite", "db": "inventory-db" }] }' \
  default_ttl="1h" \
  max_ttl="24h"
```

Ceci à créé un credential `myuser` qui permet de créer un utilisateur avec les droits `readWrite` sur la base `inventory-db` et `admin`.

```bash
vault read mongodb/creds/tester
> ...
```

## Kubernetes

### Setup

Notre application doit donc utiliser ces informations pour se connecter à la base de données.

Cependant, nous utilisons Kubernetes, il faut donc créer un role dans le connecteur Kubernetes avec une policy qui donne accès à ces creds.

1. Créer la policy

```bash
vault policy write inventory-team3-dev - <<EOF
path "database/creds/myuser" {
  capabilities = ["read"]
}
EOF
```

2. Créer le role

![Role](./role.png)

3. Créer le service account

```bash
kubectl create serviceaccount inventory-db
```

4. Ajouter le service account à la policy

```bash
...
serviceAccount: inventory-db
...
```

5. Ajouter les annotations au pod

```yaml
...
annotations:
  vault.hashicorp.com/agent-inject: "true"
  vault.hashicorp.com/agent-inject-secret-database-creds-myuser: "database/creds/myuser"
  vault.hashicorp.com/role: "inventory-db"
...
```
