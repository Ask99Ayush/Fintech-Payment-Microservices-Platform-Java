import React, { useState, useEffect } from 'react';
import { getWallet, sendPayment, genKey } from '../api/api';

function SendPayment() {
  const [wallet, setWallet] = useState(null);
  const [form, setForm] = useState({ receiverEmail: '', amount: '' });
  const [result, setResult] = useState(null);
  const [err, setErr] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => { getWallet().then(setWallet).catch(() => {}); }, []);

  const handle = e => setForm({ ...form, [e.target.name]: e.target.value });

  const send = async () => {
    if (!form.receiverEmail || !form.amount) { setErr('Please fill all fields'); return; }
    setErr(''); setResult(null); setLoading(true);
    try {
      const data = await sendPayment({
        receiverEmail: form.receiverEmail,
        amount: parseFloat(form.amount),
        idempotencyKey: genKey(),
      });
      setResult(data);
      if (data.status === 'SUCCESS') {
        getWallet().then(setWallet).catch(() => {});
      }
    } catch (e) { setErr(e.message); }
    setLoading(false);
  };

  return (
    <div>
      <div className="page-title">Send Payment</div>
      <div className="page-sub">Transfer funds instantly to any wallet</div>

      <div className="form-card">
        {wallet && (
          <div className="wallet-balance">
            <div className="label">Available Balance</div>
            <div className="amount">${parseFloat(wallet.balance).toFixed(2)}</div>
          </div>
        )}

        <div className="field">
          <label>Receiver Email</label>
          <input name="receiverEmail" placeholder="receiver@example.com" value={form.receiverEmail} onChange={handle} />
        </div>

        <div className="field">
          <label>Amount (USD)</label>
          <input name="amount" type="number" placeholder="0.00" value={form.amount} onChange={handle} />
        </div>

        <div style={{ fontSize: 12, color: 'var(--muted)', marginBottom: 16 }}>
          ℹ️ A unique payment key is auto-generated for each transaction
        </div>

        <button className="btn-gold" onClick={send} disabled={loading}>
          {loading ? 'Processing...' : 'Send Payment'}
        </button>

        {err && <div className="error-msg">{err}</div>}

        {result && (
          <div className={`result-box ${result.status === 'SUCCESS' ? 'approved' : 'rejected'}`}>
            <div className={`result-title ${result.status === 'SUCCESS' ? 'approved' : 'rejected'}`}>
              {result.status === 'SUCCESS' ? '✓ Payment Successful!' : '✗ Payment Failed'}
            </div>
            <div style={{ fontSize: 14, color: 'var(--muted)' }}>
              {result.status === 'SUCCESS'
                ? `$${result.amount} sent to ${result.receiverEmail}`
                : result.failureReason}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default SendPayment;
