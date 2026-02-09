package com.bhatia.trackify.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CarRepair
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CreditCardOff
import androidx.compose.material.icons.filled.CreditScore
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FaceRetouchingNatural
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.GifBox
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.ui.graphics.Color
import com.bhatia.trackify.model.ColorCategory
import com.bhatia.trackify.model.TransactionCategory
import com.bhatia.trackify.ui.theme.Blue
import com.bhatia.trackify.ui.theme.RaspberryRed
import com.bhatia.trackify.ui.theme.TealGreen

object TransactionData {
    val transactionTypes = listOf("Select Transaction Type", "Credit", "Debit")
    val colors = listOf(
        ColorCategory("Teal Green", TealGreen),
        ColorCategory("Blue", Blue),
        ColorCategory("Raspberry Red", RaspberryRed)
    )

    val debitCategories = listOf(
        TransactionCategory("Salary", Icons.Filled.AttachMoney, Color(0xff2ecc71)),
        TransactionCategory("Rent", Icons.Filled.HomeWork, Color(0xff3498db)),
        TransactionCategory("Groceries", Icons.Filled.ShoppingCart, Color(0xffe67e22)),
        TransactionCategory("Groceries Online", Icons.Filled.ShoppingBag, Color(0xfff1c40f)),
        TransactionCategory("Milk", Icons.Filled.LocalDrink, Color(0xffd7ccc8)),
        TransactionCategory("Water", Icons.Filled.WaterDrop, Color(0xff5dade2)),
        TransactionCategory("Electricity", Icons.Filled.Bolt, Color(0xff9b59b6)),
        TransactionCategory("Phone Expenses", Icons.Filled.Phone, Color(0xff6e2c00)),
        TransactionCategory("Internet", Icons.Filled.Wifi, Color(0xff48c9b0)),
        TransactionCategory("Fuel", Icons.Filled.LocalGasStation, Color(0xffe74c3c)),
        TransactionCategory("Gas", Icons.Filled.Kitchen, Color(0xfff39c12)),
        TransactionCategory("House Maintenance", Icons.Filled.Handyman, Color(0xff27ae60)),
        TransactionCategory("Childcare", Icons.Filled.ChildFriendly, Color(0xff85c1e9)),
        TransactionCategory("EMI", Icons.Filled.CreditCard, Color(0xfff39c12)),
        TransactionCategory("Loan Repayment", Icons.Filled.Payment, Color(0xffc0392b)),
        TransactionCategory("Insurance", Icons.Filled.Security, Color(0xffaf7ac5)),
        TransactionCategory("Subscription Services", Icons.Filled.Autorenew, Color(0xfff1c40f)),
        TransactionCategory("Credit Card Interest", Icons.Filled.CreditCardOff, Color(0xffc70039)),
        TransactionCategory("Card Bill", Icons.Filled.Receipt, Color(0xfffab1a0)),
        TransactionCategory("Food & Dining", Icons.Filled.Restaurant, Color(0xfff7dc6f)),
        TransactionCategory("Entertainment", Icons.Filled.Movie, Color(0xffa1887f)),
        TransactionCategory("Travel", Icons.Filled.Flight, Color(0xff1abc9c)),
        TransactionCategory("Clothing", Icons.Filled.Checkroom, Color(0xff2980b9)),
        TransactionCategory("Gym Membership", Icons.Filled.FitnessCenter, Color(0xff8e44ad)),
        TransactionCategory("Party Expenses", Icons.Filled.Celebration, Color(0xffd63031)),
        TransactionCategory("Concerts & Events", Icons.Filled.Event, Color(0xfff7ca18)),
        TransactionCategory("Healthcare", Icons.Filled.LocalHospital, Color(0xffe74c3c)),
        TransactionCategory("Medicines", Icons.Filled.Medication, Color(0xff154360)),
        TransactionCategory("Doctor Visits", Icons.Filled.LocalHospital, Color(0xff922b21)),
        TransactionCategory("Personal Care", Icons.Filled.FaceRetouchingNatural, Color(0xfff39c12)),
        TransactionCategory("Wellness Activities", Icons.Filled.SelfImprovement, Color(0xff82e0aa)),
        TransactionCategory("Education", Icons.AutoMirrored.Filled.MenuBook, Color(0xff2874a6)),
        TransactionCategory("Study Materials", Icons.Filled.Book, Color(0xffd2b4de)),
        TransactionCategory("Online Courses", Icons.Filled.Laptop, Color(0xff117a65)),
        TransactionCategory("Investments", Icons.AutoMirrored.Filled.TrendingUp, Color(0xffd35400)),
        TransactionCategory("Professional Courses", Icons.Filled.WorkOutline, Color(0xff5dade2)),
        TransactionCategory("Public Transport", Icons.Filled.DirectionsBus, Color(0xff935116)),
        TransactionCategory("Cab Services", Icons.Filled.DirectionsCar, Color(0xff9c640c)),
        TransactionCategory("Bike Maintenance", Icons.Filled.TwoWheeler, Color(0xffd35400)),
        TransactionCategory("Car Maintenance", Icons.Filled.CarRepair, Color(0xff873600)),
        TransactionCategory("Vehicle Repairs", Icons.Filled.CarRepair, Color(0xff800000)),
        TransactionCategory("Electronics", Icons.Filled.Devices, Color(0xff4a69bd)),
        TransactionCategory("Subscriptions", Icons.Filled.Subscriptions, Color(0xfff7dc6f)),
        TransactionCategory("Donations", Icons.Filled.VolunteerActivism, Color(0xff45b39d)),
        TransactionCategory("Charity Donations", Icons.Filled.Favorite, Color(0xffffb142)),
        TransactionCategory("Child Care", Icons.Filled.ChildCare, Color(0xff5dade2)),
        TransactionCategory("Pets", Icons.Filled.Pets, Color(0xffbc8f8f)),
        TransactionCategory("Pet Food", Icons.Filled.Fastfood, Color(0xfff39c12)),
        TransactionCategory("Pet Grooming", Icons.Filled.Face, Color(0xffaf8a75)),
        TransactionCategory("Lost/Stolen Items", Icons.Filled.ReportProblem, Color(0xff5b2c6f)),
        TransactionCategory("Gifts & Celebrations", Icons.Filled.GifBox, Color(0xff4a69bd)),
        TransactionCategory("Home Repairs", Icons.Filled.Construction, Color(0xffa04000)),
        TransactionCategory("Hobbies", Icons.Filled.Brush, Color(0xffe74c3c)),
        TransactionCategory("Miscellaneous", Icons.Filled.MoreHoriz, Color(0xffd4ac0d))
    )

    val creditCategories = listOf(
        TransactionCategory("Salary", Icons.Filled.AttachMoney, Color(0xff2ecc71)),
        TransactionCategory("Bonus", Icons.Filled.Redeem, Color(0xfff1c40f)),
        TransactionCategory("Freelance Income", Icons.Filled.WorkOutline, Color(0xff3498db)),
        TransactionCategory("Gift & Award", Icons.Filled.CardGiftcard, Color(0xffe74c3c)),
        TransactionCategory("Cashback", Icons.Filled.Savings, Color(0xff16a085)),
        TransactionCategory("Rent", Icons.Filled.HomeWork, Color(0xff9b59b6)),
        TransactionCategory("Loan", Icons.Filled.AccountBalanceWallet, Color(0xffe67e22)),
        TransactionCategory(
            "Investment Withdraw",
            Icons.AutoMirrored.Filled.TrendingUp,
            Color(0xfff39c12)
        ),
        TransactionCategory("Dividends", Icons.AutoMirrored.Filled.ShowChart, Color(0xff2980b9)),
        TransactionCategory("Royalties", Icons.AutoMirrored.Filled.LibraryBooks, Color(0xff8e44ad)),
        TransactionCategory("Sales of Assets", Icons.Filled.Storefront, Color(0xff27ae60)),
        TransactionCategory("Refunds", Icons.Filled.CreditScore, Color(0xffd35400)),
        TransactionCategory("Capital Gains", Icons.Filled.AccountBalance, Color(0xff1abc9c))
    )

    fun getCategoriesColors(category: String): Color? {
        return debitCategories.find { it.name == category }?.color
            ?: creditCategories.find { it.name == category }?.color
    }

}
