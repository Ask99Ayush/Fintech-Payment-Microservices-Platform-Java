import React, { useState, useEffect } from 'react';
import { getProfile, saveProfile } from '../api/api';

function Profile({ email }) {
  const [form, setForm] = useState({ fullName: '', phone: '', address: '' });
  const [saved, setSaved] = useState(false);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState('');

  useEffect(() => {
    getProfile()
      .then(d => { setForm({ fullName: d.fullName || '', phone: d.phone || '', address: d.address || '' }); setLoading(false); })
      .catch(() => setLoading(false));
  }, []);

  const handle = e => setForm({ ...form, [e.target.name]: e.target.value });

  const save = async () => {
    setErr('');
    try {
      await saveProfile(form);
      setSaved(true);
      setTimeout(() => setSaved(false), 2000);
    } catch (e) { setErr(e.message); }
  };

  return (
    <div>
      <div className="page-title">Profile</div>
      <div className="page-sub">{email}</div>

      <div className="form-card">
        {loading ? (
          <div className="loading">Loading...</div>
        ) : (
          <>
            <div className="profile-grid">
              <div className="field">
                <label>Full Name</label>
                <input name="fullName" value={form.fullName} onChange={handle} placeholder="John Doe" />
              </div>
              <div className="field">
                <label>Phone</label>
                <input name="phone" value={form.phone} onChange={handle} placeholder="+91 9999999999" />
              </div>
            </div>

            <div className="field">
              <label>Address</label>
              <input name="address" value={form.address} onChange={handle} placeholder="New Delhi, India" />
            </div>

            <div className="field">
              <label>Email (read only)</label>
              <input value={email} disabled style={{ opacity: 0.5 }} />
            </div>

            <button className="btn-gold" onClick={save}>
              {saved ? '✓ Saved!' : 'Save Profile'}
            </button>

            {err && <div className="error-msg">{err}</div>}
          </>
        )}
      </div>
    </div>
  );
}

export default Profile;
