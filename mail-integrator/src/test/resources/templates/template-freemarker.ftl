<!DOCTYPE html>
<html lang="en">

<head>
  <title>Welcome</title>
  <meta charset="utf-8">
  <style type="text/css">
	html,
	body {
	  width: 100%;
	  position: relative;
	  overflow-x: hidden;
	}
	body {
	  font-family: "Roboto", sans-serif;
	  color: #000;
	  margin: 0;
	  box-sizing: border-box;
	  background-color: #fff;
	  padding: 8px 0 0 0;
	  display: inline;
	}
	main {
	  width: 100%;
	  margin: 64px 0 0 0;
	}
	.section-container {
	  width: 60%;
	  max-width: 1144px;
	  margin: 0 auto;
	}
	.swipe-sessions-card {
	  width: 100%;
	  height: 600px;	  
	  overflow: hidden;
	  border-radius: 20px;
	}
	.swipe-sessions-card .card-top {
	  background: linear-gradient(101.33deg, #04943d 0.76%, #50b127 33.33%, #04943d 76.92%, #04943d 96.96%);
	  width: 100%;
	  height: 30%;
	  display: flex;
	}
	.secondary-text {
	  font-size: 14px;
	  line-height: 18px;
	  color: #adbdcc;
	  letter-spacing: 0.25px;
	}
	#sessions-logo {
	  z-index: 2;
	  width: 70%;
	  height: 70%;
	  padding: 3% 0 0 0;
	  margin: 0 auto;
	}
	.swipe-sessions-card .card-bottom {
	  width: 100%;
	  height: 70%;
	  padding: 32px;
	  box-sizing: border-box;
	  background-color: rgba(220, 220, 220, 0.15);
	}
	.swipe-sessions-card .card-bottom .subtitle {
	  font-weight: 500;
	  color: #045a9a;
	  margin: 16px 0 0 0;
	  font-size: 20px;
	  font-family: "Anek Telugu", sans-serif;
	  line-height: 26px;
	}
	.swipe-sessions-card .card-bottom .secondary-text {
	  color: #000;
	  font-weight: 700;
	  width: 100%;
	}
	.two-column {
	  width: 100%;
	  display: flex;
	  align-items: start;
	  flex-direction: row;
	  box-sizing: border-box;
	  justify-content: center;
	}
	.two-column p {
	  margin: 7px;
	}
	.two-column > .col-left {
	  width: 20%;
	  box-sizing: border-box;
	  padding: 0 16px 0 0;
	  display: flex;
	  flex-direction: column;
	  align-items: start;
	  justify-content: center;
	}
	.two-column > .col-right {
	  width: 80%;
	  box-sizing: border-box;
	  padding: 0 0 0 16px;
	  display: flex;
	  flex-direction: column;
	  align-items: start;
	  justify-content: center;
	}
	/* CUSTOM BREAKPOINT */
	@media only screen and (max-width: 1200px) {
	  .section-container {
	    width: 70%;
	  }
	}
	@media screen and (max-device-width: 640px), screen and (max-width: 640px) {
	  .two-column {
	    flex-direction: column;
	  }
	  .section-container {
		width: 99%;
	  }
	  .swipe-sessions-card {
	    height: 740px;
	  }
	  .swipe-sessions-card .card-top {
	    height: 24%;
	  }
	}
  </style>
</head>

<body>
  <main>
    <div class="section-container">
      <div class="swipe-sessions-card">
        <div class="card-top">
          <img id="sessions-logo" src="cid:logo" alt="Logo Banco">
        </div>
        <div class="card-bottom">
          <div class="card-content">
            <p class="subtitle">Simulaci&oacute;n de Pr&eacute;stamos</p>
            <p class="secondary-text">Estimado(a) Cliente,</p>
            <p class="secondary-text">Agredecemos el uso de nuestros servicios. Adjunto encontraras la Simulación del Prestamo.</p>
            <p class="secondary-text">Datos del Prestamo:</p>
            <div class="two-column">
              <div class="col-left">
                <p>Capital:</p>
              </div>
              <div class="col-right">
                <p>${amount}</p>
              </div>
            </div>
            <div class="two-column">
              <div class="col-left">
                <p>Tasa:</p>
              </div>
              <div class="col-right">
                <p>${rate}</p>
              </div>
            </div>
            <div class="two-column">
              <div class="col-left">
                <p>Plazo:</p>
              </div>
              <div class="col-right">
                <p>${repaymentTerm} ${termType}</p>
              </div>
            </div>
            <div class="two-column">
              <div class="col-left">
                <p>Vigencia:</p>
              </div>
              <div class="col-right">
                <p>Desde el ${loanStartDate} al ${loanEndDate}</p>
              </div>
            </div>
            <div class="two-column">
              <div class="col-left">
                <p>Email:</p>
              </div>
              <div class="col-right">
                <p>${email}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</body>

</html>
