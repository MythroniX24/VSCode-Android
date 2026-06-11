# 📱 VSCodroid

**VSCodroid** brings the complete Visual Studio Code (VS Code) experience directly to your Android device, running 100% locally. 

No cloud connections, no remote servers—just pure, offline coding power in the palm of your hands.

## ✨ Features

- **Local Execution:** Runs the VS Code Server natively on Android.
- **Offline First:** Code anywhere, anytime. No internet connection is required after the initial installation.
- **Language Toolchains:** Includes on-demand asset packs for your favorite programming languages:
  - ☕ Java (`toolchain_java`)
  - 🐹 Go (`toolchain_go`)
  - 💎 Ruby (`toolchain_ruby`)
  - 🐍 Python & Node.js environments
- **Extensions Support:** Install and run your favorite VS Code extensions natively on your phone or tablet.
- **Git Integration:** Full version control capabilities.

## 📂 Project Structure

```text
/
├── app/               # Main Android application module (UI and server lifecycle)
├── scripts/           # Scripts to fetch VS Code server, Termux tools, and dependencies
├── toolchain_go/      # Play Asset Delivery pack for Go language tools
├── toolchain_java/    # Play Asset Delivery pack for Java tools
├── toolchain_ruby/    # Play Asset Delivery pack for Ruby tools
├── .github/workflows/ # Automated CI/CD pipelines for building and releasing
└── docs/              # Technical specifications and implementation plans
```

## 🚀 Getting Started (For Developers)

To build VSCodroid from source, you'll need the Android SDK and Gradle.

**Important pre-requisites:** 
The project relies on external binaries for the VS Code Server and Termux tools. When building securely via GitHub Actions, these are handled automatically (see `.github/workflows/build.yml`). You can also manually fetch these assets by using the scripts found in the `scripts/` directory before compiling.

### Building the Debug APK

Compile the project directly via Gradle:

```bash
./gradlew assembleDebug
```

The resulting build will be located securely at `app/build/outputs/apk/debug/app-debug.apk`.

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! We encourage you to read the [CONTRIBUTING.md](./CONTRIBUTING.md) to see how you can help. Please ensure you also follow the project [CODE_OF_CONDUCT.md](./CODE_OF_CONDUCT.md).

## 📄 License
This project is licensed under the terms outlined in the [LICENSE](./LICENSE) file.
