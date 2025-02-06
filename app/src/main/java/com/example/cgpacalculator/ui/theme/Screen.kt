package com.example.cgpacalculator.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.style.TextOverflow
import com.example.cgpacalculator.R

@Composable
fun CGPACalculatorApp() {
    val background = painterResource(id = R.drawable.thapar)
    val redColor = Color(0xFFD50000)
    val grayColor = Color(0xFFF2F2F2)

    // State Variables
    var selectedBranch by remember { mutableStateOf("") }
    var expandedBranch by remember { mutableStateOf(false) }
    val branches = listOf("COE", "COPC", "COSE", "ECE", "ENC", "MECH", "EEC", "CHEM", "CIC", "CCA")

    var selectedCourse1 by remember { mutableStateOf("") }
    var selectedCourse2 by remember { mutableStateOf("") }
    var selectedCourse3 by remember { mutableStateOf("") }
    var selectedCourse4 by remember { mutableStateOf("") }
    var selectedCourse5 by remember { mutableStateOf("") }

    var selectedGrade1 by remember { mutableStateOf("") }
    var selectedGrade2 by remember { mutableStateOf("") }
    var selectedGrade3 by remember { mutableStateOf("") }
    var selectedGrade4 by remember { mutableStateOf("") }
    var selectedGrade5 by remember { mutableStateOf("") }

    var cgpa by remember { mutableDoubleStateOf(0.0) }

    // Data Maps
    val courseCredits = mapOf(
        "Chemistry" to 4.0,
        "Programming - C" to 4.0,
        "Electrical and Electronics" to 4.5,
        "Environment" to 2.0,
        "Mathematics 1" to 3.5
    )
    val gradePoints = mapOf(
        "A+" to 10.0,
        "A" to 10.0,
        "A-" to 9.0,
        "B" to 8.0,
        "B-" to 7.0,
        "C" to 6.0,
        "C-" to 5.0
    )

    // CGPA Calculation
    fun calculateCGPA(): Double {
        val selectedCourses = listOf(selectedCourse1, selectedCourse2, selectedCourse3, selectedCourse4, selectedCourse5)
        val selectedGrades = listOf(selectedGrade1, selectedGrade2, selectedGrade3, selectedGrade4, selectedGrade5)

        var totalCredits = 0.0
        var totalGradePoints = 0.0

        for (i in selectedCourses.indices) {
            val course = selectedCourses[i]
            val grade = selectedGrades[i]
            val credit = courseCredits[course] ?: 0.0
            if (credit > 0 && grade in gradePoints) {
                totalCredits += credit
                totalGradePoints += credit * gradePoints[grade]!!
            }
        }
        return if (totalCredits == 0.0) 0.0 else totalGradePoints / totalCredits
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = background,
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(color = redColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.thaparlogo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }

            // Branch Dropdown
            Box {
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .background(Color(0xFFF2F2F2), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .clickable { expandedBranch = !expandedBranch }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (selectedBranch.isEmpty()) "Select Branch" else selectedBranch,
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop Down Arrow",
                            tint = Color.Gray
                        )
                    }

                    DropdownMenu(
                        expanded = expandedBranch,
                        onDismissRequest = { expandedBranch = false }
                    ) {
                        branches.forEach { branch ->
                            DropdownMenuItem(
                                text = { Text(text = branch, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                onClick = {
                                    selectedBranch = branch
                                    expandedBranch = false
                                }
                            )
                        }
                    }
                }
            }

            // Course and Grade Selection
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (i in 1..5) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CourseDropdown(
                            selectedCourse = when (i) {
                                1 -> selectedCourse1
                                2 -> selectedCourse2
                                3 -> selectedCourse3
                                4 -> selectedCourse4
                                else -> selectedCourse5
                            },
                            onCourseSelected = {
                                when (i) {
                                    1 -> selectedCourse1 = it
                                    2 -> selectedCourse2 = it
                                    3 -> selectedCourse3 = it
                                    4 -> selectedCourse4 = it
                                    5 -> selectedCourse5 = it
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        GradeDropdown(
                            selectedGrade = when (i) {
                                1 -> selectedGrade1
                                2 -> selectedGrade2
                                3 -> selectedGrade3
                                4 -> selectedGrade4
                                else -> selectedGrade5
                            },
                            onGradeSelected = {
                                when (i) {
                                    1 -> selectedGrade1 = it
                                    2 -> selectedGrade2 = it
                                    3 -> selectedGrade3 = it
                                    4 -> selectedGrade4 = it
                                    5 -> selectedGrade5 = it
                                }
                            }
                        )
                    }
                }
            }

            // Calculate CGPA Button
            Button(
                onClick = { cgpa = calculateCGPA() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = redColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Calculate CGPA", color = Color.White, fontSize = 16.sp)
            }

            // Display CGPA
            Text(
                text = "CGPA: %.2f".format(cgpa),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Reset Button
            Button(
                onClick = {
                    selectedBranch = ""
                    selectedCourse1 = ""
                    selectedCourse2 = ""
                    selectedCourse3 = ""
                    selectedCourse4 = ""
                    selectedCourse5 = ""
                    selectedGrade1 = ""
                    selectedGrade2 = ""
                    selectedGrade3 = ""
                    selectedGrade4 = ""
                    selectedGrade5 = ""
                    cgpa = 0.0
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = grayColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Reset", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}

// Course Dropdown Composable
@Composable
fun CourseDropdown(selectedCourse: String, onCourseSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Chemistry", "Programming - C", "Electrical and Electronics", "Environment", "Mathematics 1")

    Box {
        Column(
            modifier = Modifier
                .width(200.dp)
                .background(Color(0xFFF2F2F2), RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable { expanded = !expanded }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (selectedCourse.isEmpty()) "Select your course" else selectedCourse,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop Down Arrow",
                    tint = Color.Gray
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { course ->
                    DropdownMenuItem(
                        text = { Text(text = course, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        onClick = {
                            onCourseSelected(course)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// Grade Dropdown Composable
@Composable
fun GradeDropdown(selectedGrade: String, onGradeSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val grades = listOf("A+", "A", "A-", "B", "B-", "C", "C-")

    Box {
        Column(
            modifier = Modifier
                .width(200.dp)
                .background(Color(0xFFF2F2F2), RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable { expanded = !expanded }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (selectedGrade.isEmpty()) "Select your grade" else selectedGrade,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop Down Arrow",
                    tint = Color.Gray
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                grades.forEach { grade ->
                    DropdownMenuItem(
                        text = { Text(text = grade, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        onClick = {
                            onGradeSelected(grade)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
