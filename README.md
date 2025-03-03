# Taroo (AI tarot reading app)

The **Tarot Reading App** is an interactive AI-powered fortune-telling experience built with **Kotlin** and **Jetpack Compose**. It allows users to ask a question, pick tarot cards, and receive insightful interpretations.

## Screenshots
<img src="https://github.com/user-attachments/assets/30a435ff-7f1f-4ce3-b651-4d0f9c64420e" width=300/>
<img src="https://github.com/user-attachments/assets/373a8cab-18fd-4849-aecc-b8e86ceb4307" width=300/>
<img src="https://github.com/user-attachments/assets/8c76a5c8-5291-44d3-ab94-ac59bffbe692" width=300/>
<img src="https://github.com/user-attachments/assets/fa4a631f-5d9f-48d5-b35d-7b5265b876f6" width=300/>
<img src="https://github.com/user-attachments/assets/a541b0f1-6e7c-4416-95a9-da7035046d57" width=300/>

## Features & Development Levels

### Core Tarot Reading Functionality
**Basic Features:**
- Users input a question (text-based).
- A shuffled deck of **78 tarot cards** is displayed.
- Users select **3 cards** by tapping.
- Card flip animation reveal the card

**Tech Stack:**
- **Kotlin** for Android development
- **Jetpack Compose** for UI 
- **LazyVerticalGrid** for deck display
- **Room DB** for storing past readings
- **Retrofit** for fetching network requests
- **Dagger-Hilt** for dependency injection
- [Tarot Api](https://tarotapi.dev/) for predefined results
- **ViewModel** for state management
---

### Advanced UI & Interactions 
**Enhanced Visual Experience:**
- **Card Flip Animation** → Tarot cards flip when selected.
- **Mystical Background Effects** → Background music effect

**Tech Stack:**
- **Jetpack animation**
- **MediaPlayer Library** for playing background music

### AI-Powered Tarot Readings & Multi-Turn Chat 🤖🔮
**AI-Enhanced Features:**
- **AI Tarot Interpretation** → Sends selected cards & user question to Gemini API.
- **Receive & Display AI Response** → AI generates a custom fortune reading.
- **Dynamic Text Styling** → AI responses feature formatted text result.
- **Multi-Turn Chat** → Users can ask follow-up questions to continue the conversation.

**Tech Stack:**
- **Gemini API** for AI-generated interpretations
- **ViewModel** for real-time UI updates

## Steps to setup project
### Installation & Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/dattasneha/taroo-app.git
   cd taroo-app
   ```
2. **Open in Android Studio (koala feature drop or above) & Build Project.**
3. **Set up the API key (Level 3 Feature):**
   - Get an API key from Gemini api.
   - Add it to `local.properties`:
     ```properties
     API_KEY=your_api_key_here
     ```
4. **Run the app on an emulator or device!**

## Download app
[Download App](https://github.com/dattasneha/taroo-app/releases/download/v1.0/taroo-app.apk)

