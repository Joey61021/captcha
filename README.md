# Captcha

A minimalistic Bukkit/Paper plugin that adds CAPTCHA verification challenges to Minecraft servers.

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

## Configuration

Configure the plugin via `plugin.yml`. Default settings work out of the box.

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

