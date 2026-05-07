import React, { useState } from 'react';
import { getToken, getEmail, saveAuth, clearAuth } from './api/api';
import Auth from './pages/Auth';
import Dashboard from './pages/Dashboard';
import SendPayment from './pages/SendPayment';
import PaymentHistory from './pages/PaymentHistory';
import Ledger from './pages/Ledger';
import FraudCheck from './pages/FraudCheck';
import Profile from './pages/Profile';
import Sidebar from './components/Sidebar';

function App() {
  const [token, setToken] = useState(getToken());
  const [email, setEmail] = useState(getEmail());
  const [page, setPage] = useState('dashboard');

  const handleLogin = (t, e) => {
    saveAuth(t, e);
    setToken(t);
    setEmail(e);
  };

  const handleLogout = () => {
    clearAuth();
    setToken(null);
    setEmail(null);
  };

  if (!token) return <Auth onLogin={handleLogin} />;

  const pages = {
    dashboard: <Dashboard />,
    send: <SendPayment />,
    payments: <PaymentHistory />,
    ledger: <Ledger />,
    fraud: <FraudCheck email={email} />,
    profile: <Profile email={email} />,
  };

  return (
    <div className="app-layout">
      <Sidebar page={page} setPage={setPage} email={email} onLogout={handleLogout} />
      <div className="main-content">
        {pages[page]}
      </div>
    </div>
  );
}

export default App;
