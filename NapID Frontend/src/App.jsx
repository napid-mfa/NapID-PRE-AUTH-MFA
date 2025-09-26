import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

// Import all page components
import Dashboard from './pages/Dashboard';
import NapidPreAuthMFA from './pages/NapidPreAuthMFA';
import AntiFraudPayment from './pages/AntiFraudPayment';


function App() { 
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/antifraud-payment" element={<AntiFraudPayment />} />
        <Route path="/napid-preauth-mfa" element={<NapidPreAuthMFA />} />
      </Routes>
    </Router>
  )
}

export default App
