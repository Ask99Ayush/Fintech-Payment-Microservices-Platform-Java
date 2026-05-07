import React from 'react';

const navItems = [
  { id: 'dashboard', icon: '⬡', label: 'Dashboard' },
  { id: 'send', icon: '↗', label: 'Send Payment' },
  { id: 'payments', icon: '≡', label: 'Payment History' },
  { id: 'ledger', icon: '◈', label: 'Ledger' },
  { id: 'fraud', icon: '⚑', label: 'Fraud Check' },
  { id: 'profile', icon: '◎', label: 'Profile' },
];

function Sidebar({ page, setPage, email, onLogout }) {
  return (
    <div className="sidebar">
      <div className="sidebar-logo">FinPay</div>
      <div className="sidebar-email">{email}</div>

      {navItems.map(item => (
        <button
          key={item.id}
          className={`nav-item ${page === item.id ? 'active' : ''}`}
          onClick={() => setPage(item.id)}
        >
          <span className="nav-icon">{item.icon}</span>
          {item.label}
        </button>
      ))}

      <button className="logout-btn" onClick={onLogout}>
        ⎋ &nbsp;Logout
      </button>
    </div>
  );
}

export default Sidebar;
