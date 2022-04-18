package com.manshal_khatri.pikadex

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.pikadex.databinding.ActivityDescriptionBinding
import com.manshal_khatri.pikadex.fragments.InfoFragment
import com.manshal_khatri.pikadex.fragments.LocationFragment
import com.manshal_khatri.pikadex.fragments.MovesFragment
import com.manshal_khatri.pikadex.model.MoveData
import com.manshal_khatri.pikadex.model.Moves
import com.manshal_khatri.pikadex.model.Pokemons
import com.squareup.picasso.Picasso
import kotlin.math.abs
var pokemon : Pokemons? = Pokemons()
class DescriptionActivity : AppCompatActivity() , GestureDetector.OnGestureListener {

    private lateinit var binding: ActivityDescriptionBinding
 private  lateinit var mBinding: ConstraintSet
 var pokeId = 4

//lateinit var list : SwipeListner
    var x1 : Float = 0.0f
    var x2 : Float = 0.0f
    var y1 : Float = 0.0f
    var y2 : Float = 0.0f
    lateinit var gd : GestureDetector
    companion object{
        const val TH = 100
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mBinding = binding.root.getConstraintSet(R.id.end )
        val t1 = mBinding.getConstraint(R.id.type1)
gd = GestureDetector(this,this)

         pokeId = intent.getIntExtra("id" , 1)
        val queue = Volley.newRequestQueue(this)

       //GlobalScope.launch {  async { getMoves(queue,pokeId) } }


        if (intent!=null){
            pokemon = pokemonsList.find {  pokeId == it.id  }
            val tmpPk = pokemon!!
            if (pokemon != null) {
                //TypeFragment().getPkmnType(tmpPk)
                binding.pokeName.text = tmpPk.pokeName
                Picasso.get().load(tmpPk.spriteUrl).into(binding.PokeSprite)

                if(tmpPk.pokeType.type2!=""){
                    binding.type2.text = tmpPk.pokeType.type2
                    binding.type1.text = tmpPk.pokeType.type1
                    setTypeTextcolor(tmpPk.pokeType.type2,binding.type2)
                    tmpPk.pokeType.type1.let { setTypeTextcolor(it,binding.type1)
                        setTypeBG(it,binding.imageView)}

                }else{
                    binding.type1.visibility= GONE
                    t1.propertySet.visibility = GONE
                    binding.type2.text = tmpPk.pokeType.type1
                    tmpPk.pokeType.type1.let { setTypeTextcolor(it,binding.type2)
                        setTypeBG(it,binding.imageView)}
                }
            }
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_moves -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,MovesFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.action_stats -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,InfoFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,LocationFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
            }
        }

        supportFragmentManager.beginTransaction().add(R.id.desc_frag_container,InfoFragment()).commit()

    }
    /*fun sendPkmn(fragment: FragmentActivity) : Pokemons? {
        if(pokemon!= null){
            return pokemon
        }else{
            return Pokemons(404)
        }
    } */

    suspend fun getMoves(queue : RequestQueue, pokeId : Int){
        val request = object : JsonObjectRequest(Method.GET, pokeApi+"$pokeId",null,Response.Listener {
            print("Api Response success $it")
             pokeMoves.clear()
            // Getting moves
            val moveslist = it.getJSONArray("moves")
            for( i in 0 until moveslist.length()){
                val move = moveslist.getJSONObject(i)
                //println(move.getJSONObject("move").getString("name"))
                val lateVer = move.getJSONArray("version_group_details")
                if(lateVer.getJSONObject(lateVer.length()-1).getJSONObject("move_learn_method").getString("name")=="level-up"){
                    pokeMoves.add( Moves(i,
                        lateVer.getJSONObject(lateVer.length()-1).getInt("level_learned_at"),
                        move.getJSONObject("move").getString("name"),
                        getMovedata(move.getJSONObject("move").getString("url"),i)
                    )
                    )

                }
            }
        },Response.ErrorListener {
            print("Api Response failed $it")
        }){

        }
        queue.add(request)
    }
     fun getMovedata(queue: RequestQueue) : MutableList<MoveData>{
        val movedata = mutableListOf<MoveData>()

            for(element in pokeMoves){
                println(element)
                val requesting = object : JsonObjectRequest(Method.GET, "",null,Response.Listener {
                    print("Api Response success $it")
                    // Getting movesdata
                    val obj = it
                    movedata.add(
                        MoveData(
                            element.mid,
                            obj.getInt("power"),
                            it.getInt("accuracy"),
                            it.getInt("pp"),"norma",
                            "Special"
                        ))
                },Response.ErrorListener {
                    println("$it OCCURED HERE")
                }){}
                queue.add(requesting)
            }
        return movedata
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
     fun getMovedata(string: String,mid : Int) : MoveData {
        var mymove : MoveData = MoveData(mid,110,100,10,"normal","kind")
        val queuem = Volley.newRequestQueue(this)
        val req = object : JsonObjectRequest(Method.GET, string,null,Response.Listener{
            print("success with move")
            mymove = MoveData(
                mid,
                it.getInt("power"),
                it.getInt("accuracy"),
                it.getInt("pp"),"norma",
                "physical"
            )
        }, Response.ErrorListener {
            print("error with move")
        }) {

        }
        queuem.add(req)

        return mymove
    }
    @SuppressLint("ResourceAsColor")
    fun setTypeTextcolor(type:String, holder : TextView){
        when(type){
            "grass" -> holder.setBackgroundResource(R.drawable.type_bg_grass)
            "poison" -> holder.setBackgroundResource(R.drawable.type_bg_poison)
            "fire" -> holder.setBackgroundResource(R.drawable.type_bg_fire)
            "water" -> holder.setBackgroundResource(R.drawable.type_bg_water)
            "electric" -> {holder.setBackgroundResource(R.drawable.type_bg_electric)
                holder.setTextColor(R.color.black)}
            "psychic" -> holder.setBackgroundResource(R.drawable.type_bg_psychic)
            "flying"-> {holder.setBackgroundResource(R.drawable.type_bg_flying)
                holder.setTextColor(R.color.black)}
            "ghost"-> holder.setBackgroundResource(R.drawable.type_bg_ghost)
            "normal"-> {holder.setBackgroundResource(R.drawable.type_bg_normal)
                holder.setTextColor(R.color.black)}
        }
    }
    fun setTypeBG(type: String, holder: ImageView){
        when(type){
            "grass" ->  holder.setImageResource(R.drawable.grass_wp)
            "poison" -> holder.setImageResource(R.drawable.posion_wp)
            "fire" -> holder.setImageResource(R.drawable.fire_wp)
            "water" -> holder.setImageResource(R.drawable.water_wp)
            "electric" -> holder.setImageResource(R.drawable.electric_wp)
            "psychic" -> holder.setImageResource(R.drawable.psychic_wp)
            "flying"-> holder.setImageResource(R.drawable.flying_wp)
            "ghost"-> holder.setImageResource(R.drawable.ghost_wp)
            "normal"-> holder.setImageResource(R.drawable.normal_wp)
        }

    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gd.onTouchEvent(event)
        when(event?.action){
            0 ->{
                x1 = event.x
                y1=event.y
            }
            1-> {
                x2 = event.x
                y2=event.y
                val v1 : Float = x2-x1
                val v2 : Float = y2-y1
                if(abs(v1) > MainActivity.TH){
                    if(x2 > x1){
                        println("RIGHT")
                        Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show()
                    }else{
                        println("LEFT")
                        Toast.makeText(this, "Left", Toast.LENGTH_SHORT).show()
                    }
                }else if(abs(v2) > MainActivity.TH){
                    if(y2 > y1){
                        println("BOTTOM")
                        Toast.makeText(this, "Bottom", Toast.LENGTH_SHORT).show()
                    }else{
                        println("TOP")
                        Toast.makeText(this, "Top", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }


    /*fun setStatusbar(){
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor=this.resources.getColor(R.color.TintSky)
    }*/
}
/*class SwipeListner(view : View,context: Context) : GestureDetector.OnGestureListener {
    var x1 = 0.0f
    var x2 = 0.0f
    var y1 = 0.0f
    var y2 = 0.0f
    companion object{
        const val TH = 100
    }

    override fun onDown(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        TODO("Not yet implemented")

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        TODO("Not yet implemented")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onTouchEvent(event : MotionEvent?) : Boolean{
        when(event?.action){
            0 ->{
                x1 = event.x
                y1=event.y
            }
            1-> {
                x2 = event.x
                y2=event.y
                val v1 = x2-x1
                val v2 = y2-y1
                if(abs(v1)>TH){
                    if(x2 > x1){
                        println("RIGHT")
                    }else{
                        println("LEFT")
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }
}*/
