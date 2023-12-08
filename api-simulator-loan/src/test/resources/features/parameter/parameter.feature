# Author: almercog@gmail.com
@parameter-service
Feature: Parameter Service
  In order to configure parameters
  As a developer
  I want to make sure parameters operations through REST API works fine

  Scenario Outline: configure loan parameters 
    Given I save <parameter>
  
  	Examples:
  	  | parameter |
  	  | "{\"code\":\"LOAN_SIMULATOR\",\"name\":\"PAYMENT_FRECUENCY\",\"description\":\"Frecuencia de Pago/Tipo de Plazo/Equivalencias TASAS efectivas\",\"value\":[{\"code\":\"A\",\"description\":\"Anual\",\"days\":360,\"paymentsPerYear\":1,\"effectiveRate\":1},{\"code\":\"S\",\"description\":\"Semestral\",\"days\":180,\"paymentsPerYear\":2,\"effectiveRate\":0.5},{\"code\":\"C\",\"description\":\"Cuatrimestral\",\"days\":120,\"paymentsPerYear\":3,\"effectiveRate\":0.3333333333},{\"code\":\"C\",\"description\":\"Trimestral\",\"days\":90,\"paymentsPerYear\":4,\"effectiveRate\":0.25},{\"code\":\"B\",\"description\":\"Bimensual\",\"days\":60,\"paymentsPerYear\":6,\"effectiveRate\":0.1666666667},{\"code\":\"M\",\"description\":\"Mensual\",\"days\":30,\"paymentsPerYear\":12,\"effectiveRate\":0.08333333333}]}" |
  	  | "{\"code\":\"LOAN_SIMULATOR\",\"name\":\"GRACE_PERIOD\",\"description\":\"Periodo de Gracia\",\"value\":[{\"code\":\"C\",\"description\":\"Capital\"},{\"code\":\"I\",\"description\":\"Capital e Interes\"}]}" |
  	  | "{\"code\":\"LOAN_SIMULATOR\",\"name\":\"REPORT_DIRECTORY\",\"description\":\"Ruta de Generacion de Archivos: PDF, Excel, CSV, TXT\",\"value\":\"/Users/giancarlo.almerco/Workspace/reports/\"}" |
  	  | "{\"code\":\"LOAN_SIMULATOR\",\"name\":\"MAIL_CONFIG\",\"description\":\"Configuracion del Servidor de Mail\",\"value\": {\"host\":\"smtp.gmail.com\",\"port\":587,\"user\":\"almercog@gmail.com\",\"pass\":\"ENC(wCrOiGx06HJHInvz9YbSDe3TCffQYYDx)\",\"props\":{\"mail.transport.protocol\":\"smtp\",\"mail.smtp.auth\":\"true\",\"mail.smtp.starttls.enable\":\"true\",\"mail.debug\":\"true\"}}}" |
  	  