# Configuração para Publicação no Maven Central via GitHub Actions

Este documento explica como configurar os secrets necessários no GitHub para publicar automaticamente no Maven Central.

## Secrets Necessários

Você precisa configurar os seguintes secrets no seu repositório GitHub:

1. Vá em **Settings** > **Secrets and variables** > **Actions**
2. Clique em **New repository secret** e adicione cada um dos seguintes:

### 1. `MAVEN_CENTRAL_USERNAME`
Seu nome de usuário do Sonatype (Maven Central).

### 2. `MAVEN_CENTRAL_PASSWORD`
Sua senha/token do Sonatype (Maven Central).

### 3. `SIGNING_KEY_ID`
O ID da sua chave GPG (geralmente os últimos 8 caracteres da chave pública).
Para obter: `gpg --list-keys` e pegue os últimos 8 caracteres do ID.

### 4. `GPG_PRIVATE_KEY`
Sua chave privada GPG exportada em formato base64.

Para exportar:
```bash
gpg --armor --export-secret-keys YOUR_KEY_ID | base64 -w 0
```

Ou no Windows PowerShell:
```powershell
gpg --armor --export-secret-keys YOUR_KEY_ID | [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes((Get-Content -Raw)))
```

### 5. `GPG_PASSPHRASE`
A senha (passphrase) da sua chave GPG.

## Como Usar

1. Configure todos os secrets acima no GitHub
2. Vá para a aba **Actions** no seu repositório
3. Selecione o workflow **Publish to Maven Central**
4. Clique em **Run workflow**
5. Selecione a branch e clique em **Run workflow**

O workflow irá:
- Fazer checkout do código
- Configurar Java e Gradle
- Importar a chave GPG
- Publicar no Maven Central

## Nota

Certifique-se de que sua chave GPG está configurada no Sonatype antes de tentar publicar.


