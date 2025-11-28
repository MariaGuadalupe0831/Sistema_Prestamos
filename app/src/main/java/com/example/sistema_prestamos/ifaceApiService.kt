package com.example.sistema_prestamos
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface ifaceApiService {

    //Para login y registro
    @FormUrlEncoded
    @POST("apiAccesoUsuario.php")
    fun registrarUsuario(
        @Field("action")action:String,
        @Field("nombre")nombreusuario:String,
        @Field("Correo")correo:String,
        @Field("contrasena")contrasena:String
    ): Call<List<clsDatosRespuesta>>

    @FormUrlEncoded
    @POST("apiAccesoUsuario.php")
    fun iniciarSesion(
        @Field("action")action:String,
        @Field("Correo")correo: String,
        @Field("contrasena")contrasena: String
    ): Call<List<clsDatosRespuesta>>

}