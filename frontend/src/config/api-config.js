// Configuración de URLs de los microservicios
// Este archivo proporciona valores por defecto si no existen variables de entorno

// URL base del gateway (puede ser localhost o TunnelMole)
const BASE_URL = process.env.REACT_APP_BASE_URL //|| 'http://localhost:8090';

const API_CONFIG = {
  // URLs construidas a partir de la base
  ADMINISTRACION: "https://mycfo-production.up.railway.app",
  CONSOLIDACION: `${BASE_URL}/consolidacion`,
  IA: "https://ia-production-e0b1.up.railway.app",
  NOTIFICACION: "https://notificacion-production.up.railway.app",
  PRONOSTICO: "https://pronostico-production.up.railway.app",
  REGISTRO: "https://registro-production-8fdb.up.railway.app",
  REPORTE: "https://reporte-production-e413.up.railway.app",
  FORECAST: `${BASE_URL}/forecast`,
  
  // WebSocket: convierte http/https a ws/wss
  WEBSOCKET: `${BASE_URL.replace(/^http/, 'ws')}/notificacion/ws`,
  
  // URL base (útil para referencias)
  BASE: BASE_URL
};

export default API_CONFIG;

