import React, { useState } from 'react';
import { fraudCheck, genKey } from '../api/api';

function FraudCheck({ email }) {
  const [form, setForm] = useState({ amount: '', referenceId: genKey() });
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState('');

  const handle = e => setForm({ ...form, [e.target.name]: e.target.value });

  const check = async () => {
    if (!form.amount) { setErr('Enter an amount'); return; }
    setErr(''); setResult(null); setLoading(true);
    try {
      const data = await fraudCheck({
        email,
        amount: parseFloat(form.amount),
        referenceId: form.referenceId,
      });
      setResult(data);
    } catch (e) { setErr(e.message); }
    setLoading(false);
  };

  return (
    <div>
      <div className="page-title">Fraud Check</div>
      <div className="page-sub">Manually verify a transaction against fraud rules</div>

      <div className="form-card">
        <div style={{ background: 'var(--bg)', borderRadius: 10, padding: '12px 16px', marginBottom: 20, fontSize: 13, color: 'var(--muted)' }}>
          <div style={{ marginBottom: 4, fontWeight: 500, color: 'var(--text)' }}>Fraud Rules</div>
          🚫 Amount &gt; $10,000 → Rejected<br />
          🚫 More than 5 transactions/minute → Rejected<br />
          🚫 Duplicate reference ID → Rejected
        </div>

        <div className="field">
          <label>Amount (USD)</label>
          <input name="amount" type="number" placeholder="0.00" value={form.amount} onChange={handle} />
        </div>

        <div className="field">
          <label>Reference ID</label>
          <input name="referenceId" value={form.referenceId} onChange={handle} />
        </div>

        <div style={{ display: 'flex', gap: 12 }}>
          <button className="btn-gold" onClick={check} disabled={loading} style={{ flex: 1 }}>
            {loading ? 'Checking...' : 'Run Fraud Check'}
          </button>
          <button className="btn-outline" onClick={() => setForm({ ...form, referenceId: genKey() })}>
            ↻ New Key
          </button>
        </div>

        {err && <div className="error-msg">{err}</div>}

        {result && (
          <div className={`result-box ${result.result === 'APPROVED' ? 'approved' : 'rejected'}`}>
            <div className={`result-title ${result.result === 'APPROVED' ? 'approved' : 'rejected'}`}>
              {result.result === 'APPROVED' ? '✓ APPROVED' : '✗ REJECTED'}
            </div>
            <div style={{ fontSize: 14, color: 'var(--muted)' }}>{result.reason}</div>
          </div>
        )}
      </div>
    </div>
  );
}

export default FraudCheck;
