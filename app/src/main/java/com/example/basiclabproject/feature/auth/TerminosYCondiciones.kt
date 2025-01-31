package com.example.basiclabproject.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TerminosYCondiciones(navController: NavController) {

    Column(
        modifier = Modifier.padding(10.dp).background(Color.Black, RoundedCornerShape(15.dp)).verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        BackButton(onBackTouch = { navController.popBackStack() })

        Text(text = "Términos y Condiciones",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(text = "1." +
                "Bienvenido a BasicLab. Estos Términos y Condiciones rigen su relación con la aplicación móvil BasicLab.\n" +
                "\n" +
                "Por favor, lea estos Términos y Condiciones detenidamente antes de utilizar nuestro Servicio. Su acceso y uso del Servicio están condicionados a su aceptación y cumplimiento de estos Términos. Estos Términos se aplican a todos los visitantes, usuarios y otras personas que accedan o utilicen el Servicio.\n" +
                "\n" +
                "Al acceder o utilizar el Servicio, usted acepta estar sujeto a estos Términos. Si no está de acuerdo con alguna parte de los Términos, entonces no podrá acceder al Servicio."
        )

        Text(text = "2. Uso del Servicio\n" +
                "2.1 Licencia de Uso\n" +
                "Le otorgamos una licencia limitada, no exclusiva, no transferible y revocable para usar el Servicio de acuerdo con estos Términos.\n" +
                "\n" +
                "2.2 Restricciones\n" +
                "Usted se compromete a no usar el Servicio para:\n" +
                "\n" +
                "Realizar actividades fraudulentas.\n" +
                "\n" +
                "Repartir o transmitir virus o cualquier otro código malicioso.\n" +
                "\n" +
                "Infringir derechos de propiedad intelectual.")

        Text(text = "3. Cuentas\n" +
                "3.1 Registro de Cuenta\n" +
                "Para utilizar ciertas características del Servicio, se le puede solicitar que se registre y proporcione información personal. Usted garantiza que la información que proporciona es precisa, actual y completa en todo momento. No hacerlo constituye una violación de los Términos, lo que puede dar lugar a la cancelación inmediata de su cuenta.\n" +
                "\n" +
                "3.2 Seguridad de la Cuenta\n" +
                "Usted es responsable de proteger la contraseña que utiliza para acceder al Servicio y de cualquier actividad o acción bajo su contraseña. Usted se compromete a no divulgar su contraseña a terceros. Debe notificarnos de inmediato si se da cuenta de cualquier infracción de seguridad o uso no autorizado de su cuenta.")

        Text(text = "4. Contenido\n" +
                "4.1 Propiedad del Contenido\n" +
                "Todo el contenido del Servicio, incluyendo texto, gráficos, logotipos, iconos, imágenes y clips de audio, es propiedad nuestra o de nuestros proveedores de contenido y está protegido por las leyes de derechos de autor y otras leyes de propiedad intelectual.\n" +
                "\n" +
                "4.2 Uso Permitido del Contenido\n" +
                "Se le otorga una licencia limitada para acceder y utilizar el contenido del Servicio para su uso personal y no comercial. Cualquier otro uso del contenido, incluyendo la reproducción, modificación, distribución, transmisión, republicación, exhibición o ejecución del contenido está estrictamente prohibido sin nuestro consentimiento previo por escrito.")

        Text(text = "5. Terminación\n" +
                "Podemos suspender o terminar su acceso al Servicio de inmediato, sin previo aviso o responsabilidad, por cualquier motivo, incluyendo, sin limitación, si usted incumple los Términos. Tras la terminación, su derecho a utilizar el Servicio cesará inmediatamente.")

        Text(text = "6. Limitación de Responsabilidad\n" +
                "En ninguna circunstancia seremos responsables de cualquier daño indirecto, incidental, especial, consecuente o punitivo, incluyendo sin limitación la pérdida de beneficios, datos, uso, buena voluntad, o cualquier otra pérdida intangible, resultante de:\n" +
                "\n" +
                "Su acceso o uso o la incapacidad de acceder o usar el Servicio.\n" +
                "\n" +
                "Cualquier conducta o contenido de terceros en el Servicio.\n" +
                "\n" +
                "Cualquier contenido obtenido del Servicio.\n" +
                "\n" +
                "Acceso, uso o alteración no autorizados de sus transmisiones o contenido.")

        Text(text = "7. Enmiendas a los Términos\n" +
                "Nos reservamos el derecho, a nuestra discreción, de modificar o reemplazar estos Términos en cualquier momento. Si una revisión es material, intentaremos proporcionar un aviso con al menos 30 días de antelación antes de que cualquier nuevo término entre en vigor. Lo que constituye un cambio material se determinará a nuestra discreción.")

        Text(text = "8. Ley Aplicable\n" +
                "Estos Términos se regirán e interpretarán de acuerdo con las leyes de [Tu País], sin tener en cuenta sus conflictos de disposiciones legales. Nuestra falta de hacer cumplir cualquier derecho o disposición de estos Términos no se considerará una renuncia a esos derechos.")

        Text(text = "9. Contacto\n" +
                "Si tiene alguna pregunta sobre estos Términos, por favor contáctenos en osnelarriaga@gmail.com.")

    }
}

//Boton regresar
@Composable
fun BackButton(onBackTouch: () -> Unit) {
    IconButton(
        onClick = onBackTouch,
        modifier = Modifier
            .padding(0.dp, 20.dp)
            .size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        )
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Button",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}