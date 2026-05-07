import React, { useState } from 'react';
import { login, signup } from '../api/api';

function Auth({ onLogin }) {
  const [tab, setTab] = useState('login');
  const [form, setForm] = useState({ email: '', password: '', fullName: '' });
  const [err, setErr] = useState('');
  const [loading, setLoading] = useState(false);

  const handle = e => setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async () => {
    setErr('');
    setLoading(true);
    try {
      const fn = tab === 'login' ? login : signup;
      const body = tab === 'login'
        ? { email: form.email, password: form.password }
        : form;
      const data = await fn(body);
      onLogin(data.token, data.email);
    } catch (e) {
      setErr(e.message);
    }
    setLoading(false);
  };

  const handleKey = e => { if (e.key === 'Enter') submit(); };

  return (
    <div className="auth-wrap">
      <div className="auth-box">
        <div className="auth-logo">Fin<span>Pay</span></div>
        <div className="auth-subtitle">Next-generation payment infrastructure</div>

        <div className="tabs">
          <button className={`tab ${tab === 'login' ? 'active' : ''}`} onClick={() => setTab('login')}>Login</button>
          <button className={`tab ${tab === 'signup' ? 'active' : ''}`} onClick={() => setTab('signup')}>Sign Up</button>
        </div>

        {tab === 'signup' && (
          <div className="field">
            <label>Full Name</label>
            <input name="fullName" placeholder="John Doe" value={form.fullName} onChange={handle} onKeyDown={handleKey} />
          </div>
        )}

        <div className="field">
          <label>Email</label>
          <input name="email" type="email" placeholder="you@example.com" value={form.email} onChange={handle} onKeyDown={handleKey} />
        </div>

        <div className="field">
          <label>Password</label>
          <input name="password" type="password" placeholder="••••••••" value={form.password} onChange={handle} onKeyDown={handleKey} />
        </div>

        <button className="btn-gold" onClick={submit} disabled={loading}>
          {loading ? 'Please wait...' : tab === 'login' ? 'Login' : 'Create Account'}
        </button>

        {err && <div className="error-msg" style={{ marginTop: 12 }}>{err}</div>}
      </div>
    </div>
  );
}

export default Auth;
