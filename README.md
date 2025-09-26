# NapID Pre-Auth MFA Demo

## ⚙️ Configuration Changes

To run the NapID Pre-Auth MFA demo, update the following files with the **backend base URL**:

1. **Android App**  
   Path: `NapID MobileApp-Android/app/src/main/java/com/napid/bobdemo/Retrofit/URLHELPER.java`  
   → Change the `baseURL` to your backend URL.

2. **Frontend App**  
   Path: `NapID Frontend/env.example`  
   → Change the `baseURL` to your backend URL.

---

## 🔑 How to Authenticate

1. Install the **NapID Demo mobile app** on your Android phone (this will display the IDs to authenticate).  
2. Open the **NapID_MFA_BOB** demo app.  
3. Click on the **NapID Anti-Fraud Shield** to launch Pre-Auth MFA.  
4. **One-Touch** the mobile number in the app to awaken the ID for 30 seconds.  
5. Select the checkbox in the **NapID Pre-Auth MFA page**.  
6. The **Verify** button will now be enabled.  
7. Click **Verify** to complete authentication successfully. ✅  

---

## 🚫 Fraud Attempt Simulation

1. Click the checkbox **without awakening the ID**.  
2. The system will display: **“Number is sleeping.”**  
3. You cannot proceed further.  

---

## 🎥 Demo Video

<p align="center">
  <a href="https://www.loom.com/share/08f95b06751d4898abd014d78e6d175f?sid=1e3789bd-8124-48cb-b5f9-cd95d3bc5bc4">
    <img src="https://cdn.loom.com/sessions/thumbnails/08f95b06751d4898abd014d78e6d175f-with-play.gif" alt="NapID Demo Video" width="600"/>
  </a>
</p>
