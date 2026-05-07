import React, { useState, useEffect } from 'react';
import { getLedger } from '../api/api';

function Ledger() {
  const [entries, setEntries] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getLedger().then(d => { setEntries(d); setLoading(false); }).catch(() => setLoading(false));
  }, []);

  return (
    <div>
      <div className="page-title">Ledger</div>
      <div className="page-sub">Immutable transaction record — append only, never modified</div>

      <div className="table-wrap">
        {loading ? (
          <div className="loading">Loading...</div>
        ) : entries.length === 0 ? (
          <div className="empty">
            <div className="empty-icon">◈</div>
            No ledger entries yet. Send a payment to see records here.
          </div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Type</th>
                <th>Amount</th>
                <th>Description</th>
                <th>Reference</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              {entries.map((e, i) => (
                <tr key={i}>
                  <td><span className={`badge ${e.type.toLowerCase()}`}>{e.type}</span></td>
                  <td>${parseFloat(e.amount).toFixed(2)}</td>
                  <td style={{ color: 'var(--muted)', fontSize: 13 }}>{e.description || '—'}</td>
                  <td style={{ color: 'var(--muted)', fontSize: 12 }}>{e.referenceId}</td>
                  <td style={{ color: 'var(--muted)', fontSize: 13 }}>{new Date(e.createdAt).toLocaleString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default Ledger;
