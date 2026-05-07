import React, { useState, useEffect } from 'react';
import { getWallet, createWallet, topUp, getPayments } from '../api/api';

function Dashboard() {
  const [wallet, setWallet] = useState(null);
  const [payments, setPayments] = useState([]);
  const [topupAmt, setTopupAmt] = useState('');
  const [msg, setMsg] = useState('');
  const [msgType, setMsgType] = useState('success');
  const [creating, setCreating] = useState(false);
  const [topping, setTopping] = useState(false);

  const loadWallet = () => getWallet().then(setWallet).catch(() => setWallet(null));
  const loadPayments = () => getPayments().then(setPayments).catch(() => {});

  useEffect(() => { loadWallet(); loadPayments(); }, []);

  const handleCreate = async () => {
    setCreating(true);
    try { await createWallet(); await loadWallet(); setMsg('Wallet created!'); setMsgType('success'); }
    catch (e) { setMsg(e.message); setMsgType('error'); }
    setCreating(false);
  };

  const handleTopup = async () => {
    if (!topupAmt || isNaN(topupAmt)) return;
    setTopping(true);
    try {
      await topUp(parseFloat(topupAmt));
      await loadWallet();
      setMsg(`$${topupAmt} added to wallet!`);
      setMsgType('success');
      setTopupAmt('');
    } catch (e) { setMsg(e.message); setMsgType('error'); }
    setTopping(false);
  };

  const success = payments.filter(p => p.status === 'SUCCESS').length;
  const failed = payments.filter(p => p.status === 'FAILED').length;

  return (
    <div>
      <div className="page-title">Dashboard</div>
      <div className="page-sub">Your financial overview</div>

      <div className="cards-row">
        <div className="card gold-card">
          <div className="card-label">Wallet Balance</div>
          <div className="card-value">{wallet ? `$${parseFloat(wallet.balance).toFixed(2)}` : '—'}</div>
          <div className="card-sub">{wallet ? wallet.currency : 'No wallet yet'}</div>
        </div>
        <div className="card">
          <div className="card-label">Successful Payments</div>
          <div className="card-value green">{success}</div>
          <div className="card-sub">All time</div>
        </div>
        <div className="card">
          <div className="card-label">Failed Payments</div>
          <div className="card-value red">{failed}</div>
          <div className="card-sub">All time</div>
        </div>
      </div>

      {!wallet ? (
        <div className="form-card" style={{ marginBottom: 24 }}>
          <div style={{ fontFamily: 'Syne', fontWeight: 700, marginBottom: 8 }}>Create Your Wallet</div>
          <div style={{ color: 'var(--muted)', fontSize: 14, marginBottom: 16 }}>
            You need a wallet to send and receive payments.
          </div>
          <button className="btn-gold" onClick={handleCreate} disabled={creating}>
            {creating ? 'Creating...' : 'Create Wallet'}
          </button>
          {msg && <div className={msgType === 'success' ? 'success-msg' : 'error-msg'}>{msg}</div>}
        </div>
      ) : (
        <div className="form-card" style={{ marginBottom: 24 }}>
          <div style={{ fontFamily: 'Syne', fontWeight: 700, marginBottom: 16 }}>Top Up Wallet</div>
          <div className="topup-row">
            <div className="field">
              <input
                type="number"
                placeholder="Amount (e.g. 500)"
                value={topupAmt}
                onChange={e => setTopupAmt(e.target.value)}
                onKeyDown={e => e.key === 'Enter' && handleTopup()}
              />
            </div>
            <button className="btn-sm" onClick={handleTopup} disabled={topping}>
              {topping ? '...' : 'Top Up'}
            </button>
          </div>
          {msg && <div className={`${msgType === 'success' ? 'success-msg' : 'error-msg'}`} style={{ marginTop: 12 }}>{msg}</div>}
        </div>
      )}

      <div className="table-wrap">
        <div className="table-head">Recent Payments</div>
        {payments.length === 0 ? (
          <div className="empty">
            <div className="empty-icon">◈</div>
            No payments yet
          </div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>To</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              {payments.slice(0, 5).map((p, i) => (
                <tr key={i}>
                  <td>{p.receiverEmail}</td>
                  <td>${parseFloat(p.amount).toFixed(2)}</td>
                  <td><span className={`badge ${p.status.toLowerCase()}`}>{p.status}</span></td>
                  <td style={{ color: 'var(--muted)', fontSize: 13 }}>{new Date(p.createdAt).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default Dashboard;
