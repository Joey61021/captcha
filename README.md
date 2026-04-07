# Captcha

A minimalistic Bukkit/Paper plugin that adds CAPTCHA verification challenges to Minecraft servers.
<img width="497" height="459" alt="Screenshot 2026-04-07 180345" src="https://github.com/user-attachments/assets/6a6636ab-f049-45a3-9ee7-cdcecc8bcb2f" />

## Features

- 🎨 **Color-matching verification** - Players must click the correct colored stained glass pane
- ⚡ **Fast & lightweight** - Built with modern Java and optimized for performance
- 🔧 **Easy integration** - Simple API for developers

## Requirements

- Java 21+
- Paper 1.21.4+

## Installation

1. Download the latest JAR from [releases](../../releases)
2. Place it in your server's `plugins` folder
3. Restart the server

## API Usage

```java
CaptchaService captcha = new CaptchaService();
captcha.open(player); // Opens CAPTCHA for player
```

## Building

```bash
mvn clean package
```

The compiled JAR will be in the `target/` directory.

## License

This project is provided as-is.

